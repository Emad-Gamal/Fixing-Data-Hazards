import java.util.ArrayList;
import java.util.List;


public class TestDependecyGraph {
    public static void main(String[] args) {
        testWithGenericInt();
        testWithGenericString();
    }

    public static void testWithGenericInt() {
        final List<Integer> nodeValueList = new ArrayList<Integer>();
        Graph<Integer> graph = new Graph<Integer>(new NodeValueListener<Integer>() {
            public void evaluating(Integer nodeValue) {
                nodeValueList.add(nodeValue);
            }
        });
        graph.addDependency(1, 2);
        graph.addDependency(1, 4);
        graph.addDependency(2, 4);
        graph.addDependency(3, 5);
        graph.addDependency(5, 8);
        graph.addDependency(2, 7);
        graph.addDependency(2, 9);
        graph.addDependency(2, 8);
        graph.addDependency(9, 10);
        graph.generateDependencies();

        System.out.println(nodeValueList);

    }

    public static void testWithGenericString() {
        final List<String> nodeValueList = new ArrayList<String>();
        Graph<String> graph = new Graph<String>(new NodeValueListener<String>() {
            public void evaluating(String nodeValue) {
                nodeValueList.add(nodeValue);
            }
        });
        graph.addDependency("a", "b");
        graph.addDependency("a", "c");
        graph.addDependency("a", "f");
        graph.addDependency("c", "d");
        graph.addDependency("d", "g");
        graph.addDependency("f", "d");
        graph.addDependency("h", "e");
        graph.generateDependencies();
        System.out.println(nodeValueList);

    }
}
