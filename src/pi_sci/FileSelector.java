/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi_sci;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author kyk
 */
public class FileSelector extends JFrame{
    Container container1 = getContentPane();           //设置一个容器    
    
    private JPanel panel1 = new JPanel(new GridLayout(1,1));    // use to put j2    
    private JLabel j2 = new JLabel("注意：待处理论文清单的最前两列用来标注单位排名和通讯单位，分别命名为SignatureUnit和CorrespondenceUnit");
    
    private JPanel panel2 = new JPanel(new GridLayout(1,3));    // use to put j2
    private JLabel j3 = new JLabel("请选择待处理论文类型");
    private JRadioButton radio1 = new JRadioButton("SCI");// 定义一个单选按钮       
    private JRadioButton radio2 = new JRadioButton("EI");// 定义一个单选按钮 
    private ButtonGroup group = new ButtonGroup();
    
    private JPanel panel3 = new JPanel(new GridLayout(1,2));    // use to put jb1 and jt1
    private JButton jb1 = new JButton("请选择待处理的论文：");    
    private JTextField jt1 = new JTextField("",20);
    
    private JPanel panel4 = new JPanel(new GridLayout(1,2));    // use to put jb2 and jt2
    private JButton jb2 = new JButton("请选择地址清单文件：");
    private JTextField jt2 = new JTextField("",20);
    
    private JPanel panel5 = new JPanel(); 
    private JButton jb3 = new JButton("开始");
    
    private RadioButtonListener radioButtonListener=new RadioButtonListener();
	
    private String paperStyle=null;
    
    public FileSelector() {
        //将整个容器设置为5行1列的网格布局,网格布局管理器x,y代表行和列
        container1.setLayout(new GridLayout(5,1));
	
        panel1.add(j2);
        panel2.add(j3);
        panel2.add(radio1);
        panel2.add(radio2);        
        radio1.addActionListener(radioButtonListener);
        group.add(radio1);
        group.add(radio2);
	panel3.add(jb1);
	panel3.add(jt1);
	panel4.add(jb2);
	panel4.add(jt2);
        jb3.setPreferredSize(new Dimension(100,40));
        panel5.add(jb3);
		
	container1.add(panel1);
        container1.add(panel2);
        container1.add(panel3);
        container1.add(panel4);
        container1.add(panel5);
        
	jb1.addActionListener(new ChooseFile(jt1));
        jb2.addActionListener(new ChooseFile(jt2));
        jb3.addActionListener(new AddressSelect());
        
        radio1.addActionListener(radioButtonListener);
        radio2.addActionListener(radioButtonListener);
        
        setTitle("文件选择窗口");
        this.setSize(500, 300);
        this.setLocation(150,75);
        this.setVisible(true);
        // 实现按掉右上角的×后整个程序自动退出
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);        
    }
    public class ChooseFile implements ActionListener{
        private JTextField jt;
        
        public ChooseFile(JTextField jtext){
            jt = jtext;
        }
       	@Override
       	public void actionPerformed(ActionEvent e){
            JFileChooser jf = new JFileChooser();  
            jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
            jf.showDialog(new JLabel(), "选择");  
            File f =  jf.getSelectedFile();//使用文件类获取选择器选择的文件 
            String s = f.getAbsolutePath();//返回路径名  
            jt.setText(s);
        }
    }
    
    public class AddressSelect implements ActionListener{
        @Override
  	public void actionPerformed(ActionEvent e){
            String paperFile = jt1.getText();
            String addressFile = jt2.getText();
            switch(paperStyle){
                case "SCI":{
                    SCIProcessing fp = new SCIProcessing(paperFile,addressFile);
                    fp.sciIdentify();
                    dispose();          // 关闭当前窗口
                    break;
                }
                case "EI":{
                    EIProcessing fp = new EIProcessing(paperFile,addressFile);
                    fp.eiIdentify();
                    dispose();          // 关闭当前窗口
                    break;
                }
                default:{
                    System.out.println("Please select the paper style, SCI or EI.");
                    break;
                }
            }
	}    
    }
    
    public class RadioButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            JRadioButton temp=(JRadioButton)arg0.getSource();
            if(temp.isSelected()){
                paperStyle=temp.getText();
            }             
        }         
    }    
}
