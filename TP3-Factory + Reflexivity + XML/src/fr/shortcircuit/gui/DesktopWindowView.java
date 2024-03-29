package fr.shortcircuit.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import fr.shortcircuit.model.ProductElement;
import fr.shortcircuit.xml.IXMLFactory;
import fr.shortcircuit.xml.IXMLObject;
import fr.shortcircuit.xml.MyDomParser;
import fr.shortcircuit.xml.MySaxParser;
import fr.shortcircuit.xml.MySaxReflecter;

public class DesktopWindowView extends JFrame implements IXMLViewConstants
{
	public JDesktopPane 											desk;
	
	public MySaxReflecter<? extends Collection<ProductElement>>		saxReflectedParserV;
	
	private int 													xOffset = 0;
	private int 													yOffset	= 0;
	
	
	public DesktopWindowView() 
	{
		super("j-CSI");
		
		createDesktopPane();
		createToolbarAndMenu();
	}

	public void createDesktopPane()
	{	 
		desk			= new JDesktopPane();
		
		this.getContentPane().add(desk, BorderLayout.CENTER);
		
		desk.putClientProperty("JDesktopPane.dragMode", "faster");

		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing (WindowEvent e) 
			{
				System.out.println("Bye !");
				desk.setVisible(false);
				//desk.dispose();
				System.exit(0);
			}
		}
		);
	}

	public void createToolbarAndMenu()
	{
		JMenuBar 	menuBar				= new JMenuBar();
		JToolBar 	toolBar				= new JToolBar(SwingConstants.HORIZONTAL);
		JMenu 		menuAbout			= new JMenu("About");	
		JMenuItem 	menuAboutContent	= new JMenuItem("About j-CSI");
		JButton 	butSaxReflecterV	= new JButton("SAX Reflecter (Vector)");
		
		toolBar.putClientProperty("JToolbar.isRollover", Boolean.TRUE);
		
		menuAbout.add(	menuAboutContent);
		menuBar.add(	menuAbout);
				 
		toolBar.add(butSaxReflecterV);
		
		//AboutMessage 
		menuAboutContent.addActionListener(	new ActionListener()	{public void actionPerformed(ActionEvent e) {JOptionPane.showMessageDialog(getParent(), "Mini projet j-CSI\nGroupe: hamsou_l & chopar_s\r\nEpitech Montpellier - Promo 2015");}});		

		//Reflecter
		butSaxReflecterV.addActionListener(	new ActionListener() 	{public void actionPerformed(ActionEvent e)	{buildVectorSaxReflecter(); 	createJifTree(saxReflectedParserV);  createJifText(saxReflectedParserV, "Fichier XML", 400);}});
		
		//finalize
		this.setJMenuBar(menuBar);
		this.getContentPane().add(toolBar, BorderLayout.NORTH);	
	}

	/**
	 * A partir des objets issus du doc xml lu, 
	 * on construit un JTree en utilisant le champ type pour trier les entrees.
	 */ 
 	public void createJifTree(MySaxReflecter<? extends Collection> saxReflectedParser)
	{	
		TreeContent treeEltType		= new TreeContent(saxReflectedParser.getMapType());
		TreeContent treeEltGenre	= new TreeContent(saxReflectedParser.getMapGenre());
		
		treeEltType.setPreferredSize(	new Dimension(200, 250));
		treeEltGenre.setPreferredSize(	new Dimension(200, 250));

		JSplitPane splitCenter		= new JSplitPane(JSplitPane.VERTICAL_SPLIT, treeEltType, treeEltGenre);
		splitCenter.setDividerLocation(0.70);
					        						//resize, close, max, icone
		JInternalFrame jifTree		= new JInternalFrame("Arbre XML des Sites ", true, true, true, true);
				
		jifTree.getContentPane().add(splitCenter, BorderLayout.CENTER);
		
		desk.add(jifTree, 1);

		splitCenter.doLayout();		
		splitCenter.updateUI();
	
		jifTree.setVisible(true);
		jifTree.pack();
	
		jifTree.setBounds(550 + xOffset, 50 + yOffset, 250, 550);								
		
		jifTree.getContentPane().doLayout();		
		jifTree.updateUI();		
		
		incrementOffsets();
	}
	
	public void createJifText(IXMLFactory xmlFactory, String title, int height)
	{
		StringBuffer bufXmlContent	= new StringBuffer();
		JTextArea txaXmlContent		= new JTextArea();
		JScrollPane jsp				= new JScrollPane(txaXmlContent);
		JInternalFrame jifText		= new JInternalFrame(title, true, true, true, true);
				
		for (IXMLObject o : xmlFactory.getCollectionElt())
			bufXmlContent.append(o.toXmlString() + "\r\n");		

		txaXmlContent.setText(bufXmlContent.toString());
		
		jifText.getContentPane().setLayout(new BorderLayout());				
		jifText.getContentPane().add(jsp, BorderLayout.CENTER);				
		desk.add(jifText, 1);

		jifText.setVisible(true);
		jifText.pack();	
		jifText.setBounds(50 + xOffset, 50 + yOffset, 450, height);
	
		incrementOffsets();
	}

	private void incrementOffsets()
	{
		xOffset	+=50;
		yOffset	+=50;		
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//XML Parser building
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void buildVectorSaxReflecter()	{this.saxReflectedParserV	= new MySaxReflecter<Vector<ProductElement>>(	new Vector<ProductElement>(),	 "doc/xml/products2.xml");}
}
