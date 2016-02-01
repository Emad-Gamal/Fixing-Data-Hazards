import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class Graph<T> {
    
    private HashMap<T, GraphNode<T>> nodes = new HashMap<T, GraphNode<T>>();
    
    private NodeValueListener<T> listener;
    
    private List<GraphNode<T>> evaluatedNodes = new ArrayList<GraphNode<T>>();

    public Graph(NodeValueListener<T> listener) {
        this.listener = listener;
    }
    
    public void addDependency(T evalFirstValue, T evalAfterValue) {
        GraphNode<T> firstNode = null;
        GraphNode<T> afterNode = null;
        if (nodes.containsKey(evalFirstValue)) {
            firstNode = nodes.get(evalFirstValue);
        } else {
            firstNode = createNode(evalFirstValue);
            nodes.put(evalFirstValue, firstNode);
        }
        if (nodes.containsKey(evalAfterValue)) {
            afterNode = nodes.get(evalAfterValue);
        } else {
            afterNode = createNode(evalAfterValue);
            nodes.put(evalAfterValue, afterNode);
        }
        firstNode.addGoingOutNode(afterNode);
        afterNode.addComingInNode(firstNode);
    }
    
    private GraphNode<T> createNode(T value) {
        GraphNode<T> node = new GraphNode<T>();
        node.value = value;
        return node;
    }
    
    public void generateDependencies() {
        List<GraphNode<T>> endNodes = getEndNodes();
        List<GraphNode<T>> nextNodesToDisplay = new ArrayList<GraphNode<T>>();
        for (GraphNode<T> node : endNodes) {
            listener.evaluating(node.value);
            evaluatedNodes.add(node);
            nextNodesToDisplay.addAll(node.getGoingOutNodes());
        }
        generateDependencies(nextNodesToDisplay);
    }

    private void generateDependencies(List<GraphNode<T>> nodes) {
        List<GraphNode<T>> nextNodesToDisplay = null;
        for (GraphNode<T> node : nodes) {
            if (!isAlreadyEvaluated(node)) {
                List<GraphNode<T>> comingInNodes = node.getComingInNodes();
                if (areAlreadyEvaluated(comingInNodes)) {
                    listener.evaluating(node.value);
                    evaluatedNodes.add(node);
                    List<GraphNode<T>> goingOutNodes = node.getGoingOutNodes();
                    if (goingOutNodes != null) {
                        if (nextNodesToDisplay == null)
                            nextNodesToDisplay = new ArrayList<GraphNode<T>>();

                        nextNodesToDisplay.addAll(goingOutNodes);
                    }
                } else {
                    if (nextNodesToDisplay == null)
                        nextNodesToDisplay = new ArrayList<GraphNode<T>>();
                    nextNodesToDisplay.add(node);
                }
            }
        }
        if (nextNodesToDisplay != null) {
            generateDependencies(nextNodesToDisplay);
        }
    }
  
    private boolean isAlreadyEvaluated(GraphNode<T> node) {
        return evaluatedNodes.contains(node);
    }

    private boolean areAlreadyEvaluated(List<GraphNode<T>> nodes) {
        return evaluatedNodes.containsAll(nodes);
    }

    private List<GraphNode<T>> getEndNodes() {
        List<GraphNode<T>> endNodes = null;
        Set<T> keys = nodes.keySet();
        for (T key : keys) {
            GraphNode<T> node = nodes.get(key);
            if (node.getComingInNodes() == null) {
                if (endNodes == null)
                    endNodes = new ArrayList<GraphNode<T>>();
                endNodes.add(node);
            }
        }
        return endNodes;
    }
}