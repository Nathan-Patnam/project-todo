
package edu.wofford;
public class Demo {
        
    
          
       
    
            public static void main(String[] args) {
                
                ArgumentParser argchecker = new ArgumentParser("VolumeCalculator");  

                argchecker.setArgs("length width height", "7 5 2");
                int length=Integer.parseInt(argchecker.getArgs("length"));
                int width=Integer.parseInt(argchecker.getArgs("width"));
                int height=Integer.parseInt(argchecker.getArgs("height"));
                int volume= length*width*height;

                System.out.println("The length arguement is " + String.valueOf(length));
                System.out.println("The width arguement is " + String.valueOf(width));
                System.out.println("The height arguement is " + String.valueOf(height));
                System.out.println("The volume is" + String.valueOf(volume));
    
                
        
                
            }
        }