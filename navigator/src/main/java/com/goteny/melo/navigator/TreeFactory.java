package com.goteny.melo.navigator;

import com.goteny.melo.navigator.annotation.Next;

import java.lang.reflect.Method;

public class TreeFactory
{


    public TreeParent<NodePage> createTree(Class<? extends PageListener> rootClxx)
    {

        TreeParent<NodePage> tree = new TreeParent<>(new NodePage(rootClxx, null));
        TreeParent.Node<NodePage> nodeRoot = tree.root();

        getAllNode(rootClxx, tree, nodeRoot);

//        TreeParent.Node<String> nodeA = tree.addNode(PageA.class.getName(), nodeRoot);
//        TreeParent.Node<String> nodeB = tree.addNode(PageB.class.getName(), nodeRoot);
//
//        TreeParent.Node<String> nodeC = tree.addNode(PageC.class.getName(), nodeA);
//        TreeParent.Node<String> nodeD = tree.addNode(PageD.class.getName(), nodeA);
//
//        TreeParent.Node<String> nodeE = tree.addNode(PageD.class.getName(), nodeD);
//        TreeParent.Node<String> nodeF = tree.addNode(PageD.class.getName(), nodeD);

        int i = 0;
        i++;

        return tree;
    }


    private void getAllNode(Class<? extends PageListener> clxxParent, TreeParent<NodePage> tree, TreeParent.Node<NodePage> nodeParent)
    {
        Method[] methods = clxxParent.getDeclaredMethods();

        for (Method method: methods)
        {
            Next next = method.getAnnotation(Next.class);

            if (next != null)
            {
                if (next.value() != null)
                {
                    Class<? extends PageListener> nextClxx = next.value();

                    TreeParent.Node<NodePage> node = tree.addNode(new NodePage(nextClxx, method.getName()), nodeParent);

                    //防止死循环，上溯所有父节点，如果存在同名父节点，则跳到下一个循环
                    boolean isParentNode = hasNode(nextClxx, tree, nodeParent);

                    if (isParentNode)
                        continue;

                    getAllNode(nextClxx, tree, node);
                }
            }
        }
    }

    private boolean hasNode(Class<? extends PageListener> clxx, TreeParent<NodePage> tree, TreeParent.Node<NodePage> nodeParent)
    {
        if (nodeParent == null)
            return false;


        if (nodeParent.data.pageClass.equals(clxx))
        {
            return true;
        }else {
            return hasNode(clxx, tree, tree.node(nodeParent.parent));
        }
    }
}
