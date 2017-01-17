
from collections import OrderedDict

def init(graph):

    def is_not_visited_node(node, visited_nodes):
        return True if node not in visited_nodes else False

    def has_no_children(node, graph):
        return True if node not in graph else False

    def traverse(nodes, visited_nodes):

        for node in nodes:
            if is_not_visited_node(node, visited_nodes):
                if has_no_children(node, graph):
                    visited_nodes.append(node)
                else:
                    traverse(graph[node], visited_nodes)

        return visited_nodes

    visited_nodes = []
    for node, values in graph.iteritems():
        visited_nodes.append(node)
        traverse(graph[node], visited_nodes)
    print visited_nodes


if __name__ == "__main__":
    graph = OrderedDict({"S": ["A", "B", "C"],
             "A": ["D"],
             "C": ["D"],
             "B": ["D"],
             "C": ["E", "F", "G", "H"]})
    init(graph)
