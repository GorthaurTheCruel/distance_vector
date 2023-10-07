public class Entity1 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity1()
    {
        currentNode = 1;
        neighbors = new int[] { 0, 2 };

        preInitDistanceTable();
        initializeKnownCosts();
        distanceVector = getDistanceVector();
        sendUpdateToAllNeighbors();
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {
        int senderNode = p.getSource();
        for (int i = 0; i < distanceTable.length ; i++) {
            int[] distancesToNodeViaAll = distanceTable[i];
            distancesToNodeViaAll[senderNode] = distanceTable[senderNode][currentNode] + p.getMincost(i);
        }

        int[] newDistanceVector = getDistanceVector();
        if (hasCostChanged(distanceVector, newDistanceVector)) {
            distanceVector = newDistanceVector;
            sendUpdateToAllNeighbors();
        }
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("         via");
        System.out.println(" D1 |   0   2");
        System.out.println("----+--------");
        for (int i = 0; i < NetworkSimulator.NUMENTITIES; i++)
        {
            if (i == 1)
            {
                continue;
            }
            
            System.out.print("   " + i + "|");
            for (int j = 0; j < NetworkSimulator.NUMENTITIES; j += 2)
            {
            
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}
