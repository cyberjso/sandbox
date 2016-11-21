
def init(graph):

    def traverse(node, graph, visited_nodes, stack):

        if len(stack) == 0 and len(visited_nodes) > 0:
            return visited_nodes
    
        if node in visited_nodes:
            updated_stack = list(set(stack) -  set([node]))
            traverse(stack.pop(), graph, visited_nodes, updated_stack)
        else:
            stack.append(node)
            visited_nodes.append(node)
            if node  not in graph:
                traverse(node, graph, visited_nodes, stack)
            else:
                for child_node in graph[node]:
                    traverse(child_node, graph, visited_nodes, stack)

    stack = []
    visited_nodes = []
    for node in graph:
        visited_nodes.append(traverse(node, graph, visited_nodes, stack))


if __name__ == "__main__":
    graph = {}
    graph = {"S": ["A", "B", "C"],
             "A": ["D"],
             "C": ["D"],
             "B": ["D"]}
    init(graph)
