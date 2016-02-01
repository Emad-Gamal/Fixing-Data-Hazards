import java.util.ArrayList;
import java.util.List;

final class GraphNode<T> {
    public T value;
    private List<GraphNode<T>> comingInNodes;
    private List<GraphNode<T>> goingOutNodes;

    
    public void addComingInNode(GraphNode<T> node) {
        if (comingInNodes == null)
            comingInNodes = new ArrayList<GraphNode<T>>();
        comingInNodes.add(node);
    }

    
    public void addGoingOutNode(GraphNode<T> node) {
        if (goingOutNodes == null)
            goingOutNodes = new ArrayList<GraphNode<T>>();
        goingOutNodes.add(node);
    }

    
    public List<GraphNode<T>> getComingInNodes() {
        return comingInNodes;
    }

    public List<GraphNode<T>> getGoingOutNodes() {
        return goingOutNodes;
    }
}
