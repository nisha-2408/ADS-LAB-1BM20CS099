#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
struct Node
{
int data;
struct Node* npx;
};
struct Node* XOR (struct Node *a, struct Node *b)
{
return (struct Node*) ((uintptr_t) (a) ^ (uintptr_t) (b));
}
void insert(struct Node **head_ref, int data)
{
struct Node *new_node = (struct Node *) malloc (sizeof (struct Node) );
new_node->data = data;
new_node->npx = *head_ref;
if (*head_ref != NULL)
{
(*head_ref)->npx = XOR(new_node, (*head_ref)->npx);
}
*head_ref = new_node;
}
struct Node* delete(struct Node *head){
    struct Node *prev, *temp;
    if(head==NULL){
        printf("\nEmpty List");
    } else {
        temp = head;
        printf("Node with value of %d deleted ", head->data);
        temp = XOR(head->npx, NULL);
        temp->npx = XOR(XOR(temp->npx, head), NULL);
        head = temp;
        printf("%d", head->data);
    }
    return head;
}
void printList (struct Node *head)
{
struct Node *curr = head;
struct Node *prev = NULL;
struct Node *next;

printf ("\nFollowing are the nodes of Linked List: \n");

while (curr != NULL)
{
printf ("%d ", curr->data);
next = XOR (prev, curr->npx);
prev = curr;
curr = next;
}
}
int main ()
{
struct Node *head = NULL;
    int opt , n;
    do {
        printf("\nEnter your option: \n");
        printf("1. Insert\n2. Delete\n3. Display\n4.Exit\n");
        scanf("%d", &opt);
        switch(opt){
            case 1: printf("\nEnter your data: ");
                    scanf("%d", &n);
                    insert(&head, n);
                    break;
            case 2: head = delete(head);
                    break;
            case 3: printList(head);
                    break;
            case 4: exit(0);
                    break;
        }

    }while(opt!=4);

return (0);
}