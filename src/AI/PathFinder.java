package AI;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class PathFinder {
    
    GamePanel gamePanel;
    Node[][] node;
    ArrayList<Node> openList = new ArrayList<>();
    public ArrayList<Node> pathList = new ArrayList<>();
    Node startNode, goalNode, currentNode;
    boolean goalReached = false;
    int step = 0;

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        initNode();
    }

    private void initNode() {
        node = new Node[gamePanel.MAX_WORLD_COLUMN][gamePanel.MAX_WORLD_ROW];

        int col = 0;
        int row = 0;

        while(col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {
            node[col][row] = new Node(col, row);
            col++;
            if(col == gamePanel.MAX_WORLD_COLUMN) {
                col = 0;
                row++;
            }
        }
    }

    private void resetAlgorythmData() {
        int col = 0;
        int row = 0;

        while(col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {
            node[col][row].open = false;
            node[col][row].checked = false;
            node[col][row].solid = false;

            col++;
            if(col == gamePanel.MAX_WORLD_COLUMN) {
                col = 0;
                row++;
            }
        }

        openList.clear();
        pathList.clear();
        goalReached = false;
        step = 0;
    }

    public void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
        resetAlgorythmData();
        
        startNode = node[startCol][startRow];
        goalNode = node[goalCol][goalRow];
        
        currentNode = startNode;
        openList.add(currentNode);

        int col = 0;
        int row = 0;

        while(col < gamePanel.MAX_WORLD_COLUMN && row < gamePanel.MAX_WORLD_ROW) {

            int tileNum = gamePanel.tileManager.mapTileNumber[gamePanel.currentMap][col][row];
            if(gamePanel.tileManager.tiles[tileNum].collision) {
                node[col][row].solid = true;
            }
            col++;
            if(col == gamePanel.MAX_WORLD_COLUMN) {
                col = 0;
                row++;
                if(row == gamePanel.MAX_WORLD_ROW) {
                    break;
                }
            }
            for(int i = 0; i < gamePanel.interactiveTile[1].length; i++){
                if(gamePanel.interactiveTile[gamePanel.currentMap][i] != null && gamePanel.interactiveTile[gamePanel.currentMap][i].destrucible) {
                    int itCol = (int)(gamePanel.interactiveTile[gamePanel.currentMap][i].worldX/gamePanel.tileSize);
                    int itRow = (int)(gamePanel.interactiveTile[gamePanel.currentMap][i].worldY/gamePanel.tileSize);
                    node[itCol][itRow].solid = true;
                }
            }
            calculateCost(node[col][row]);
        }

    }
    
    private void calculateCost(Node node) {

        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        node.fCost = node.gCost + node.hCost;
    }

    public boolean search() {

        int col = 0;
        int row = 0;
        int bestNodeIndex = 0;
        int bestNodefCost = 0;

        while(!goalReached && step < 500) {
            col = currentNode.col;
            row = currentNode.row;

            currentNode.checked = true;
            openList.remove(currentNode);

            if(row - 1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if(col - 1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if(row + 1 < gamePanel.MAX_WORLD_ROW) {
                openNode(node[col][row + 1]);
            }
            if(col + 1 < gamePanel.MAX_WORLD_COLUMN) {
                openNode(node[col + 1][row]);
            }

            bestNodeIndex = 0;
            bestNodefCost = 999;

            for(int i = 0; i < openList.size(); i++) {
                if(openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                } else if(openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    } 
                }
            }

            if(openList.size() == 0) {
                break;
            }

            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
            step++;
        }
        return goalReached;
    }

    private void openNode(Node node) {

        if(!node.open && !node.checked && !node.solid) {
            node.open = true;
            node.parent = currentNode;
            openList.add(node);

        }
    }

    private void trackThePath() {
        Node current = goalNode;
        while (current != startNode) {
            pathList.add(0,current);
            current = current.parent;
        }
    }
}
