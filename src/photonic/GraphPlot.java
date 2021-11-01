package photonic;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.math.plot.Plot2DPanel;
import org.math.plot.plotObjects.BaseLabel;

import flanagan.complex.Complex;
import flanagan.complex.ComplexMatrix;
import flanagan.math.Matrix;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static java.lang.Math.PI;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.Font;

public class GraphPlot extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	JFrame frame;
	JButton button;

	int 	ctr = 0,	c = 0,	ctr1=0;
	int N[]  = new int[5];
	double N1[] = new double[5];
	double N2[] = new double[5];
	double D3[] = new double[5];
	double D4[] = new double[5];

	double n1,n2,d3,d4;
	int M1;
	double TT1[] = new double[1001];
	double TT11[][] = new double[10][1001];

	double[] dd1;
	double[] dd2;
	double[] dd3;

	double lambda[] = new double[1001];
	double[] db=new double[100];
	int n[]=new int[50];

	String s[][] = {{"n1", "n2"}, {"d1", "d2"}, {"N",""}};
	JLabel label[][] = new JLabel[3][2];

	JTextField t1,t2,t3,t4,t5;
	JInternalFrame internalFrame;

	Plot2DPanel G_plot = new Plot2DPanel();

	//URL url2 = PhMain.class.getResource( "/Img/blo.png");


	GraphPlot(){

		//	FRAME

	    frame = new JFrame();
	    frame.getContentPane().setEnabled(false);
	    frame.getContentPane().setForeground(Color.WHITE);
	    frame.setTitle("Transmitivity Of 1D Photonic Crystal");
	    //frame.setIconImage(Toolkit.getDefaultToolkit().getImage(url2));
	    frame.setResizable(false);
	    frame.setAutoRequestFocus(true);

	    frame.getContentPane().setBackground(new Color(176, 224, 200));
	    frame.getContentPane().addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentShown(ComponentEvent arg0) {
	        }
	    });

	    frame.setBounds(100, 100, 955, 545);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(null);
	    frame.getRootPane().setDefaultButton(button);

	    button = new JButton("GET PLOT");
	    button.setToolTipText("CLICK AND GET THE GRAPHICAL PLOT");
	    button.setBounds(78, 420, 110, 40);
	    frame.getContentPane().add(button);
	    button.addActionListener(this);

	    //	TEXT FIELDS

	    t1 = new JTextField();
	    t1.setBounds(78, 80, 110, 25);
	    frame.getContentPane().add(t1);
	    t1.setColumns(10);

	    t2 = new JTextField();
	    t2.setBounds(78, 116, 110, 25);
	    frame.getContentPane().add(t2);
	    t2.setColumns(10);

	    t3 = new JTextField();
	    t3.setDoubleBuffered(true);
	    t3.setColumns(10);
	    t3.setBounds(78, 214, 110, 25);
	    frame.getContentPane().add(t3);

	    t4 = new JTextField();
	    t4.setColumns(10);
	    t4.setBounds(78, 250, 110, 25);
	    frame.getContentPane().add(t4);

	    t5 = new JTextField();
	    t5.setBounds(78, 346, 110, 24);
	    frame.getContentPane().add(t5);
	    t5.setColumns(10);

	    JLabel headlabel = new JLabel("SET INPUT VALUES");
	    headlabel.setForeground(new Color(51, 51, 153));
	    headlabel.setBackground(Color.WHITE);
	    headlabel.setFont(new Font("Simplified Arabic", Font.BOLD, 18));
	    headlabel.setBounds(30, 30, 190, 23);
	    Font font = headlabel.getFont();
	    Map attributes = font.getAttributes();
	    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	    headlabel.setFont(font.deriveFont(attributes));
	    frame.getContentPane().add(headlabel);

	    for (int i = 0; i < 3; i++) {
	        for (int j = 0; j < 2; j++) {
	            label[i][j] = new JLabel(s[i][j]);
	            label[i][j].setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 17));
	            label[i][j].setForeground(new Color(51, 51, 153));
	            label[i][j].setBounds(30, 81 + 36 * (i + j) + c, 38, 24);
	            frame.add(label[i][j]);
	        }
	        c = c + 98;
	    }

	    label[0][0].setToolTipText("Value of n1 must be in between 1 to 1.5 ");
	    label[0][1].setToolTipText("Value of n2 must be in between 1 to 1.2 ");
	    label[1][0].setToolTipText("Value of d1 must be in between 1 to 5 micrometer");
	    label[1][1].setToolTipText("Value of d2 must be in between 0.2 to 1.5 micro meter ");
	    label[2][0].setToolTipText("Value of N must be 7,11 or 15 ");

	    // internal JFRAME
	    //URL url1 = PhMain.class.getResource("/Img/A.png");
	    //ImageIcon icon = new ImageIcon(url1);

	    internalFrame = new JInternalFrame("THE GRAPHICAL PLOT");
	    internalFrame.setEnabled(false);
	    internalFrame.setMaximizable(true);
	    //internalFrame.setFrameIcon(icon);
	    internalFrame.setBorder(new LineBorder(new Color(176, 224, 200), 1));
	    //internalFrame.setBorder(new LineBorder(Color.ORANGE, 3));
	    internalFrame.getContentPane().setFont(new Font("Sylfaen", Font.PLAIN, 18));
	    internalFrame.getContentPane().setBackground(new Color(248, 248, 255));
	    internalFrame.add(G_plot);
	    internalFrame.getContentPane().addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentHidden(ComponentEvent arg0) {
	        }
	    });
	    internalFrame.setBounds(294, 30, 627, 461);
	    frame.getContentPane().add(internalFrame);
	    internalFrame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
	    if(ae.getSource() == button)
	    {


	        n1 = Double.valueOf(t1.getText());
	        n2 = Double.valueOf(t2.getText());
	        d3 = Double.valueOf(t3.getText());
	        d4 = Double.valueOf(t4.getText());
	        M1 = Integer.parseInt(t5.getText());



	        internalFrame.getContentPane().addComponentListener(new ComponentAdapter() {
	            @Override
	            public void componentHidden(ComponentEvent arg0) {
	            }
	        });

		  try{
	              M1 =Integer.parseInt(t5.getText());
	              if (!(M1==11||M1==7||M1==15)){
	        	  throw new ArithmeticException();
	               }

	         }
	      catch(ArithmeticException ae1)

	        {
	            JOptionPane.showMessageDialog(this,"N must be 7,11 or15","Error",JOptionPane.ERROR_MESSAGE);
	             return;
	        }
	      catch(Exception ee)

	        {
	    	  JOptionPane.showMessageDialog(this,"Must fill TextFiled of 'N'","Error",JOptionPane.ERROR_MESSAGE);
	          return;
	        }



		  try{
			      n1=Double.valueOf(t1.getText());
			      if (!(n1>=1&&n1<=1.6)){
				  throw new ArithmeticException();
			      }

	         }
	      catch(ArithmeticException ae2)

	        {
	            JOptionPane.showMessageDialog(this,"n1 must be in between 1 to 1.6","Error",JOptionPane.ERROR_MESSAGE);
	             return;
	        }
	      catch(Exception ee)
		    {
	    	     JOptionPane.showMessageDialog(this,"Must fill TextFiled of 'n1'","Error",JOptionPane.ERROR_MESSAGE);
	             return;
	         }


		  try{
			   n2=Double.valueOf(t2.getText());

	         }
	      catch(Exception ee)

	      {
	    	  JOptionPane.showMessageDialog(this,"Must fill TextFiled of 'n2'","Error",JOptionPane.ERROR_MESSAGE);
	          return;
	      }
		  try{
			  d3=Double.valueOf(t3.getText());

	        }
	     catch(Exception ee)

	     {
	   	  JOptionPane.showMessageDialog(this,"Must fill TextFiled of 'd1'","Error",JOptionPane.ERROR_MESSAGE);
	         return;
	     }
		  try{
			  d4=Double.valueOf(t4.getText());

	        }
	     catch(Exception ee)

	     {
	   	  JOptionPane.showMessageDialog(this,"Must fill TextFiled of 'd2'","Error",JOptionPane.ERROR_MESSAGE);
	         return;
	     }
		  if (n2>=1&&n2<=1.2){
			   if (d3>=1&&d3<=5){
				   if (d4>=0.2&&d4<=1.5){
					   ctr++;
					   if(ctr>3){
						   ctr=1;
					   }
					   n[ctr]=M1;
					   db[4*(ctr-1)+1]=n1;
					   db[4*(ctr-1)+2]=n2;
					   db[4*(ctr-1)+3]=d3;
					   db[4*(ctr-1)+4]=d4;

						 G_plot.removeAllPlots();
						 Graph_Plot();

				   }
				   else
					   JOptionPane.showMessageDialog(this,"Value of d2 must be in between 0.2 to 1.5 micro meter","Error",JOptionPane.ERROR_MESSAGE);
			            return;
			   }
			   else
				   JOptionPane.showMessageDialog(this,"Value of d1 must be in between 1 to 5 micrometer","Error",JOptionPane.ERROR_MESSAGE);
		            return;
		   }
		   else
			   JOptionPane.showMessageDialog(this,"Value of n2 must be in between 1 to 1.2","Error",JOptionPane.ERROR_MESSAGE);
	            return;
	    }

	}

	public void  Graph_Plot(){

		  for(int ct=1;ct<=ctr;ct++){
		  	N[ctr] 	= M1;
		  	N1[ctr] = n1;
		  	N2[ctr] = n2;
		  	D3[ctr] = d3;
		  	D4[ctr] = d4;

		  	double k = 1.75E-6;
		    double j = 1.3E-6;
		    double p = (k - j) / 1000;

		    for(int i=0;i<1001;i++){

		        lambda[i] = j;
		        j = j + p;

		        double theta1 = 10 * PI / 180, theta2 = Math.asin(((N2[ct] * Math.sin(theta1)) / N1[ct]));
		        double d31 = D3[ct]*(1E-6);
		        double d41 = D4[ct]*(1E-6);
		        double a1 = Math.cos(theta1), a2 = Math.cos(theta2);

		        double d1 = d31 / a1, d2 = d41 / a2;

		        double r12 = ((N1[ct] * a1) - (N2[ct] * a2))/((N1[ct] * a1) + (N2[ct] * a2));
		        double r21 = -r12;

		        double t12 = Math.sqrt(Math.abs(1 - r12 * r12));
		        double t21 = Math.sqrt(Math.abs(1 - r21 * r21));
		        //System.out.println(t12);

		        Matrix A = new Matrix(new double[][]{{1, r12}, {r12, 1}});
		        Matrix B = new Matrix(new double[][]{{1, r21}, {r21, 1}});

		        Matrix M12 = A.times(1 / t12);
		        Matrix M21 = B.times(1 / t21);


		        double k1 = (2 * PI * n1 / lambda[i]);
		        double k2 = (2 * PI * n2 / lambda[i]);

		        double t = -(1 / (t12 * t21));
		        //System.out.println(t);

		        Complex eye1 = new Complex(Math.cos(k1 * d1), Math.sin(k1 * d1));
		        Complex eye2 = new Complex(Math.cos(k1 * d1), -Math.sin(k1 * d1));

		        Complex eye11 = eye1.times(r21);
		        Complex eye22 = eye2.times(r21);

		        Complex eye3 = new Complex(Math.cos(k2 * d2), Math.sin(k2 * d2));
		        Complex eye4 = new Complex(Math.cos(k2 * d2), -Math.sin(k2 * d2));

		        Complex eye33 = eye3.times(r12);
		        Complex eye44 = eye4.times(r12);

		        Complex e1 = (eye1.times(eye3)).plus(eye22.times(eye33)).times(t);
		        Complex e2 = (eye1.times(eye44)).plus(eye22.times(eye4)).times(t);
		        Complex e3 = (eye11.times(eye3)).plus(eye2.times(eye33)).times(t);
		        Complex e4 = (eye11.times(eye44)).plus(eye2.times(eye4)).times(t);

		        ComplexMatrix ML1 = new ComplexMatrix(new flanagan.complex.Complex[][]{{e1,e2},{e3,e4}});

		        Complex e5 = (eye3.times(eye1)).plus(eye44.times(eye11)).times(t);
		        Complex e6 = (eye3.times(eye22)).plus(eye44.times(eye2)).times(t);
		        Complex e7 = (eye33.times(eye1)).plus(eye4.times(eye11)).times(t);
		        Complex e8 = (eye33.times(eye22)).plus(eye4.times(eye2)).times(t);

		        ComplexMatrix ML2 = new ComplexMatrix(new flanagan.complex.Complex[][]{{e5,e6},{e7,e8}});

		        Complex t11 = new Complex(ML1.getElementCopy(0,0));
		        Complex t22 = new Complex(ML1.getElementCopy(0,1));
		        Complex t33 = new Complex(ML1.getElementCopy(1,0));
		        Complex t44 = new Complex(ML1.getElementCopy(1,1));
		        //System.out.println(t11+"\t\t"+t22+"\t\t"+t33+"\t\t"+t44);

		        ComplexMatrix K = new ComplexMatrix(ML1.copy());

		        for(int z=0;z<N[ct];z++) {
			              ML1 = ML1.times(K);
			          }
		        ComplexMatrix Mtot1 = new ComplexMatrix(ML1.copy());

		        Complex t1 = new Complex(Mtot1.getElementCopy(0,0));
		        Complex t2 = new Complex(Mtot1.getElementCopy(0,1));
		        Complex t3 = new Complex(Mtot1.getElementCopy(1,0));
		        Complex t4 = new Complex(Mtot1.getElementCopy(1,1));

		        //System.out.println(t1+"\t\t"+t2+"\t\t"+t3+"\t\t"+t4);
		        Complex tt1 = t1.inverse();                                 //INVERSE of the first element of the matrix.

		        //System.out.println(tt1);
		        Complex T1 = tt1.pow(2.0);

		        //System.out.println(T1);
		        TT1[i] = T1.abs();

		        //System.out.println(TT1[i]);
		        TT11[ct][i] = Math.log(TT1[i]);

		        //System.out.println(TT1[i]);
		    }

		    if(ct==1){
					 dd1=TT11[1];
		  }
		 if(ct==2){
			dd2=TT11[2];
		 }
		 if(ct==3){
			dd3=TT11[3];
		 }
		}

		  if(ctr==1){

			  G_plot.removeLegend();
			  G_plot.addLegend("SOUTH");
		 	  G_plot.addLinePlot("plot1",Color.BLUE, lambda, dd1);

		 	 	}

		 	if(ctr==2){

		 		G_plot.removeLegend();
		 		G_plot.addLegend("SOUTH");
		 	    G_plot.addLinePlot("plot1",Color.BLUE, lambda, dd1);
		 		G_plot.addLinePlot("plot2",Color.RED, lambda, dd2);

		 	 	}


		 	if(ctr==3){

		 	G_plot.removeLegend();
		 	G_plot.addLegend("SOUTH");
		    G_plot.addLinePlot("plot1",Color.BLUE, lambda, dd1);
			G_plot.addLinePlot("plot2",Color.RED, lambda, dd2);
			G_plot.addLinePlot("plot3",new Color(0,204,102), lambda,dd3);

		 	}

		        BaseLabel title = new BaseLabel(" Graph plot ", Color.BLACK, 0.5, 1.1);
		        title.setFont(new Font("Courier",Font.PLAIN, 14));
		        G_plot.addPlotable(title);
		        G_plot.setAxisLabels("lambda", "Transmitivity");
		        G_plot.plotCanvas.setBackground(Color.WHITE);

		        //change X Axis
		        G_plot.getAxis(0).setColor(Color.BLUE.darker());
		        G_plot.getAxis(0).setLightLabelAngle(-PI / 4);
		        G_plot.getAxis(0).setLabelPosition(0.5, -0.15);

		        //change Y Axis
		        G_plot.getAxis(1).setColor(Color.BLUE.darker());
		        G_plot.getAxis(1).setLightLabelAngle(-PI / 4);
		        G_plot.getAxis(1).setLabelPosition(-0.15, 0.5);
		        G_plot.getAxis(1).setLabelAngle(-PI / 2);
			}
	}