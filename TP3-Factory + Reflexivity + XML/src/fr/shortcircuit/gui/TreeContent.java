package fr.shortcircuit.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import fr.shortcircuit.model.Actor;
import fr.shortcircuit.model.DvdElement;
import fr.shortcircuit.model.GameElement;
import fr.shortcircuit.model.K7Element;
import fr.shortcircuit.model.ProductElement;


public class TreeContent extends JPanel implements MouseListener
{
	//Data Storer
	public Map<String, ProductElement>					mapEntry;
	
	//Declaration des differents objets propres au JTree: Gui, Model,  
	public JTree										tree;
	public DefaultMutableTreeNode						rootNode;
	public DefaultTreeModel								treeModel;

	public Vector										vectorSelectedSiblings;
		
	//Differentes references utilisees pour le rendu graphique de l'objet.	
	public ImageIcon									iconExpanded, iconCollapsed, rootIcon, leafIcon;

	public static ImageIcon			iconRoot			= new ImageIcon("doc/images/root.png");
	public static ImageIcon			iconType			= new ImageIcon("doc/images/list.png");
	public static ImageIcon			iconDvd				= new ImageIcon("doc/images/dvd.png");
	public static ImageIcon			iconK7				= new ImageIcon("doc/images/k7.png");
	public static ImageIcon			iconGame			= new ImageIcon("doc/images/game.png");
	public static ImageIcon			iconActor			= new ImageIcon("doc/images/actor.png");

	public JScrollPane 									jsp;
	
	public Color color									= Color.black;
	public Color highlight								= new Color(0, 0, 0);

	public int total									= 0;

	
	public TreeContent(Map<String, Collection<ProductElement>> mapEntry) 
	{	
		stateOn();
		
		createStructures(mapEntry);
		
		createTree();
		
		calculateBonusForTree();
	}

	public void stateOn()
	{
		this.setLayout(new GridLayout(1,1));	
	}
	
	public void createStructures(Map mapEntry)
	{
		this.mapEntry									= mapEntry;
		
		vectorSelectedSiblings							= new Vector();
	}
	
	public void createTree() 
	{
		rootNode										= new DefaultMutableTreeNode("Elt(s)");
		this.treeModel									= new DefaultTreeModel(rootNode);
		this.tree										= new JTree(treeModel);

		try
		{
			//permet de trier un tableau d'objets "Comparable", et ainsi eviter les problemes de Map...	
			//Arrays.sort(mapEntry.keySet().toArray());
			//
			//Methode surchargee permettant d'utiliser un "Comparator", ici une reference static vers la Classe tres utile java.util.Collections :)
			//Arrays.sort(mapEntry.keySet().toArray(), Collections.reverseOrder());
			
			for (String key : mapEntry.keySet())
			{
								
				Collection<ProductElement> collectionContent	= (Collection) mapEntry.get(key);
				DefaultMutableTreeNode listNode					= new DefaultMutableTreeNode(key);
				
				treeModel.insertNodeInto(listNode, rootNode, rootNode.getChildCount());
				
				if (collectionContent != null)
				{
					int index								= 0;
					
					for (ProductElement elt : collectionContent)
					{
						DefaultMutableTreeNode productNode			= new DefaultMutableTreeNode(elt);
						treeModel.insertNodeInto(productNode, listNode, index++);

						int jndex								= 0;

						for (Actor a : elt.getActors())
						{
							DefaultMutableTreeNode actorNode			= new DefaultMutableTreeNode(a);
							treeModel.insertNodeInto(actorNode, productNode, jndex++);
							
						}					
					}
				}
			}
		}
		catch (Exception e) {System.out.println("treeTravaux createTree " + e.toString()); /*e.printStackTrace();*/}
		
		jsp												= new JScrollPane(tree);
		add(jsp, 0);
		
		//affectation d'un treeCellRenderer a la volee dans une classe inner
		tree.setCellRenderer(new TreeCellRenderer() 
		{
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
			{
				//System.out.println("root depth " + ((DefaultMutableTreeNode) ((TreeNode) tree.getModel().getRoot())).getDepth() + ", node " + value.toString() + " Depth " + ((DefaultMutableTreeNode) value).getDepth() + ", level " + ((DefaultMutableTreeNode) value).getLevel());
					
				JLabel lblContent						= new JLabel();
				DefaultMutableTreeNode	nodeValue		= (DefaultMutableTreeNode) value;
				
				lblContent.setBackground((selected)? Color.lightGray 	: Color.white);
				lblContent.setForeground((selected)? Color.blue 		: Color.black);		

				lblContent.setPreferredSize(new Dimension(150, 30));
				
				//autre methode disponible dans l'API
				//lblContent.setText(tree.convertValueToText(value, selected, expanded, leaf, row, hasFocus));
				lblContent.setText(value.toString());
				
				if (nodeValue.isRoot())
					lblContent.setIcon(iconRoot);
				else
					lblContent.setIcon(getIconForType(nodeValue.getUserObject()));
							
				return lblContent;
			}
		}	
		);
		
		tree.addMouseListener(this);
	}

	public ImageIcon getIconForType(Object o)
	{
		if (o instanceof String)
			return iconType;

		if (o instanceof DvdElement)
			return iconDvd;

		if (o instanceof K7Element)
			return iconK7;
		
		if (o instanceof GameElement)
			return iconGame;

		if (o instanceof Actor)
			return iconActor;
		
		return null;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Business logic
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Calcul par lien dynamique lors de l'appel de la methode calculateBonus() :
	 * 
	 * K7: 		redefinie la methode avec un coefficient de bonus de 15
	 * DVD:		ne redefinie pas la methode, et c'est donc celle de la class mere (productElement) qui est appelee)
	 * Game: 	redefinie la methode avec un coefficient de bonus de 20 et un second bonus additif de 15
	 */ 
	public void calculateBonusForTree()
	{
		Enumeration enumTree							= rootNode.breadthFirstEnumeration();
						
		while (enumTree.hasMoreElements())
		{
			DefaultMutableTreeNode node					= (DefaultMutableTreeNode) enumTree.nextElement();
			
			if (node.getUserObject() instanceof ProductElement)
			{
				ProductElement eltProduct				= (ProductElement) node.getUserObject();
				
				total									+= 	eltProduct.calculateBonus();
			}
		}	
	
		System.out.println("TreeContent: calculateBonusForTree: total " + total);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Mouse Events
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void mouseClicked(MouseEvent e)	{}
	public void mouseEntered(MouseEvent e)	{}
	public void mouseExited(MouseEvent e)	{}
	public void mousePressed(MouseEvent e)	{}
	
	public void mouseReleased(MouseEvent e)	
	{
		if (e.getSource() instanceof JTree)
			handleMouseTree(e);
	}
	
	/**
	 * Cette methode permet de presenter la deuxieme interface importante dans les JTree: les TreePath
	 * 
	 * Ces derniers permettent de materialiser un chemin choisi par l'utilisateur dans l'arbre
	 * A partir d'un TreePath on peut rapidement obtenir le TreeNode associe, et donc son objet associe, 
	 * bien entendu cette derniere manipulation necessite deux casts afin de parvenir a ses fins.
	 */ 
	public void handleMouseTree(MouseEvent e)
	{
		//System.out.println("tree.getSelectionPath().getPathCount(): " + tree.getSelectionPath().getPathCount() + " , " + tree.getSelectionPath().getLastPathComponent().toString());
		
		TreePath tp														= tree.getSelectionPath();

		//System.out.println("tp.getPathCount() " + tp.getPathCount());

		if ((tp != null) && (tp.getPathCount() == 3))
		{
			ProductElement eltProduct								= (ProductElement) ((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent()).getUserObject();
				
			System.out.println("ProductElement: " + eltProduct.toString());	
		}

		this.updateUI();		
	}

}
