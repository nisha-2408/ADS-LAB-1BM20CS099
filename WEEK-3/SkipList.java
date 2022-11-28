import java.util.*;

class newList {
    static class Node {
        int key;
        Node forward[];

        Node(int key, int lvl) {
            this.key = key;
            forward = new Node[lvl + 1];
        }
    }

    static class SkipList {
        int MAXLVL;
        float P;
        int level;
        Node header;

        SkipList(int max, float f) {
            this.MAXLVL = max;
            this.P = f;
            level = 0;
            this.header = new Node(-1, MAXLVL);
        }

        int randomLevel() {
            float r = (float) Math.random();
            int lvl = 0;
            while (r < P && lvl < MAXLVL) {
                lvl++;
                r = (float) Math.random();
            }
            return lvl;
        }

        Node createNode(int key, int level) {
            Node n = new Node(key, level);
            return n;
        }

        void insertElement(int key) {
            Node current = header;
            Node update[] = new Node[MAXLVL + 1];
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].key < key) {
                    current = current.forward[i];
                }
                update[i] = current;
            }
            current = current.forward[0];

            if (current == null || current.key != key) {
                int rlevel = randomLevel();
                if (rlevel > level) {
                    for (int i = level + 1; i < rlevel + 1; i++) {
                        update[i] = header;
                    }
                    level = rlevel;
                }
                Node n = new Node(key, rlevel);
                for (int i = 0; i <= rlevel; i++) {
                    n.forward[i] = update[i].forward[i];
                    update[i].forward[i] = n;
                }
                System.out.println("Successfully inserted key: " + key);
            }
        }

        void deleteNode(int key) {
            Node current = header;
            Node update[] = new Node[MAXLVL + 1];
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].key < key) {
                    current = current.forward[i];
                }
                update[i] = current;
            }
            current = current.forward[0];
            if (current != null && current.key == key) {
                for (int i = 0; i <= level; i++) {
                    if (update[i].forward[i] != current) {
                        break;
                    }
                    update[i].forward[i] = current.forward[i];
                }
            } else {
                System.out.println("Key: " + key + " not found");
                return;
            }
            while (level > 0 && header.forward[level] == null) {
                level--;
            }
            System.out.println("Successfully deleted: " + key);
        }

        void searchKey(int key) {
            int count=0;
            Node current = header;
            for (int i = level; i >= 0; i--) {
                while (current.forward[i] != null && current.forward[i].key < key) {
                    count++;
                    current = current.forward[i];
                }
            }
            current = current.forward[0];
            if (current != null && current.key == key) {
                count++;
                System.out.println("Key: " + key + " found with "+count+" comparisons");
            } else {
                System.out.println("Key: " + key + " not found");
            }
        }

        void displayList() {
            System.out.println("*******SkipList*******");
            for (int i = 0; i <= level; i++) {
                Node node = header.forward[i];
                System.out.print("Level " + i + ":");
                while (node != null) {
                    System.out.print(node.key + " ");
                    node = node.forward[i];
                }
                System.out.println();
            }
        }
    }

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the maximum level of the skiplist: ");
        int n = sc.nextInt();
        SkipList lst = new SkipList(n, 0.5f);
        int opt, key;
        do{
            System.out.println("Enter the valid option: ");
            System.out.printf("1.Insert\n2.Delete\n3.Search\n4.Display\n5.Exit\n");
            opt = sc.nextInt();
            switch(opt){
                case 1: System.out.println("Enter the key you want to insert: ");
                        key = sc.nextInt();
                        lst.insertElement(key);
                        break;
                case 2: System.out.println("Enter the key you want to delete: ");
                        key = sc.nextInt();
                        lst.deleteNode(key);
                        break;
                case 3: System.out.println("Enter the key you want to search: ");
                        key = sc.nextInt();
                        lst.searchKey(key);
                        break;
                case 4: lst.displayList();
                        break;
                case 5: System.out.println("Exitting....");
                        break;
                default:System.out.println("Please enter a valid option!");
            }
        }while(opt!=5);
        sc.close();
    }
}