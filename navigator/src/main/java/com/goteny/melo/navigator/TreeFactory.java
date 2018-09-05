package com.goteny.melo.navigator;

import com.goteny.melo.navigator.annotation.Next;

import java.lang.reflect.Method;

public class TreeFactory
{


    public TreeParent<String> createTree(Class<?> rootClxx)
    {

        TreeParent<String> tree = new TreeParent<>(rootClxx.getName());
        TreeParent.Node<String> nodeRoot = tree.root();

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


    private void getAllNode(Class<?> clxxParent, TreeParent<String> tree, TreeParent.Node<String> nodeParent)
    {
        Method[] methods = clxxParent.getDeclaredMethods();

        for (Method method: methods)
        {
            Next next = method.getAnnotation(Next.class);

            if (next != null)
            {
                if (next.value() != null)
                {
                    Class nextClxx = next.value();

                    TreeParent.Node<String> node = tree.addNode(nextClxx.getName(), nodeParent);

                    //防止死循环，上溯所有父节点，如果存在同名父节点，则跳到下一个循环
                    boolean isParentNode = hasNode(nextClxx, tree, nodeParent);

                    if (isParentNode)
                        continue;

                    getAllNode(nextClxx, tree, node);
                }
            }
        }
    }

    private boolean hasNode(Class clxx, TreeParent<String> tree, TreeParent.Node<String> nodeParent)
    {
        if (nodeParent == null)
            return false;


        if (nodeParent.data.equals(clxx.getName()))
        {
            return true;
        }else {
            return hasNode(clxx, tree, tree.node(nodeParent.parent));
        }
    }
}
