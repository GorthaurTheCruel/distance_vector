import java.security.InvalidParameterException;

public abstract class Entity
{
    // Each entity will have a distance table
    protected int[][] distanceTable = new int[NetworkSimulator.NUMENTITIES]
                                           [NetworkSimulator.NUMENTITIES];

    
    // The update function.  Will have to be written in subclasses by students
    public abstract void update(Packet p);
    
    // The link cost change handler.  Will have to be written in appropriate
    // subclasses by students.  Note that only Entity0 and Entity1 will need
    // this, and only if extra credit is being done
    public abstract void linkCostChangeHandler(int whichLink, int newCost);

    // Print the distance table of the current entity.
    protected abstract void printDT();

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

}
