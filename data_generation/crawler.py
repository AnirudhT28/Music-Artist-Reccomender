import pylast
import networkx as nx
import time
import sqlite3
import os

# CONFIGURATION
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, "..", "artist_graph.db")

API_KEY = "48ce68fed763d353780ba4d180ebf94e"
API_SECRET = "4e7a09835335854f3cd32188c18848a6"

def build_and_save_graph(seed_artists, depth=2, max_neighbors=15): 
    network = pylast.LastFMNetwork(api_key=API_KEY, api_secret=API_SECRET)
    G = nx.Graph()
    visited = set()
    listener_counts = {} 
    queue = [(artist, 0) for artist in seed_artists]

    print(f"Starting crawl...")
    while queue:
        current_name, current_depth = queue.pop(0)
        node_id = current_name.lower()

        if node_id in visited or current_depth >= depth:
            continue

        try:
            print(f"Crawling: {current_name}")
            artist_obj = network.get_artist(current_name)

            # Fetch popularity
            listener_counts[current_name] = artist_obj.get_listener_count()

            similar = artist_obj.get_similar(limit=max_neighbors)
            for item in similar:
                neighbor_name = item.item.get_name()
                # Add edge to the graph
                G.add_edge(current_name, neighbor_name, weight=float(item.match))
                if neighbor_name.lower() not in visited:
                    queue.append((neighbor_name, current_depth + 1))

            visited.add(node_id)
            time.sleep(0.3) 
        except Exception as e:
            print(f"Error processing {current_name}: {e}")

    if len(G) > 0:
        print(f"Calculating PageRank for {len(G)} artists...")
        scores = nx.pagerank(G, weight='weight')

        conn = sqlite3.connect(DB_PATH)
        c = conn.cursor()
        
        # Create the tables
        c.execute('CREATE TABLE IF NOT EXISTS artists (name TEXT PRIMARY KEY, score REAL, listeners INTEGER)')
        c.execute('CREATE TABLE IF NOT EXISTS influences (artist_name TEXT, influenced_name TEXT)')
        
        # Clear old influences to avoid duplicates if re-running
        c.execute('DELETE FROM influences')

        # Save Artists
        artist_data = [
            (name, score, listener_counts.get(name, 0))
            for name, score in scores.items()
        ]
        c.executemany('REPLACE INTO artists VALUES (?,?,?)', artist_data)

        # Save Influences (Edges)
        # We iterate through the graph's edges and save them to the DB
        edge_data = []
        for u, v in G.edges():
            edge_data.append((u, v))
            edge_data.append((v, u)) # Since it's an undirected graph, we save both directions for easy SQL joins

        c.executemany('INSERT INTO influences VALUES (?,?)', edge_data)
        
        conn.commit()
        conn.close()
        print(f"Success! Database updated at {DB_PATH}")
        print(f"Saved {len(artist_data)} artists and {len(edge_data)} connections.")

if __name__ == "__main__":
    seeds = [
        "Radiohead",          
        "Kaytranada",         
        "Miles Davis",        
        "MF DOOM",            
        "Aphex Twin",         
        "Cocteau Twins",     
        "Fela Kuti",         
        "Björk",              
        "Bad Bunny",          
        "Dolly Parton"
        "Sabrina Carpenter"        
    ]
    build_and_save_graph(seeds, depth=3, max_neighbors=25)