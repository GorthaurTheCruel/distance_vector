import java.security.InvalidParameterException;
import java.util.Arrays;

public abstract class Entity
{
    // Each entity will have a distance table
    protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES]
                                           [NetworkSimulator.NUMENTITIES];

    // Current entity index
    protected int currentNode;

    // Array containing direct neighbors of the current entity
    protected int[] neighbors;

    // Latest calculated distance vector for the current entity
    protected int[] distanceVector;

    // The update function.  Will have to be written in subclasses by students
    public abstract void update(Packet p);
    
    // The link cost change handler.  Will have to be written in appropriate
    // subclasses by students.  Note that only Entity0 and Entity1 will need
    // this, and only if extra credit is being done
    public abstract void linkCostChangeHandler(int whichLink, int newCost);

    // Print the distance table of the current entity.
    protected abstract void printDT();

    // Initialize all the distance table values to 999
    protected void preInitDistanceTable() {
        for (int[] ints : distanceTable) {
            Arrays.fill(ints, 999);
        }
    }

    // Sets the initial known costs for the current node
    protected void initializeKnownCosts() {
        distanceTable[currentNode][currentNode] = Costs.initialCosts[currentNode][currentNode];
        for (int neighbor : neighbors) {
            distanceTable[neighbor][currentNode] = Costs.initialCosts[neighbor][currentNode];
        }
    }

    // Compare two distance vectors and returns a flag indicating whether the cost has changed
    protected boolean hasCostChanged(int[] previousVector, int[] newVector) {
        if (previousVector.length != NetworkSimulator.NUMENTITIES || newVector.length != NetworkSimulator.NUMENTITIES) {
            System.out.println("Previous Distance vector length = " + previousVector.length + ", new distance vector length = " + newVector.length);
            throw new InvalidParameterException("The distance vector is invalid");
        }
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++) {
            if (previousVector[i] != newVector[i]) {
                return true;
            }
        }
        return false;
    }

    // Gets the minimum cost (distance vector) for each node
    protected int[] getDistanceVector() {
        int[] distanceVector = new int[distanceTable.length];
        for (int i = 0; i < distanceTable.length; i++) {
            int[] distancesToNode = distanceTable[i];
            int minDistance = Arrays.stream(distancesToNode).min().orElse(999);
            distanceVector[i] = minDistance;
        }
        return distanceVector;
    }

    // Creates a new packet and sends it to all neighbors of the current node
    protected void sendUpdateToAllNeighbors() {
        for (int neighbor : neighbors) {
            Packet packet = new Packet(currentNode, neighbor, distanceVector);
            NetworkSimulator.toLayer2(packet);
        }
    }

}
