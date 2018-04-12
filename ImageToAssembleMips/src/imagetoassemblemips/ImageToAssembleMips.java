/* Thanks: Agradeço a DEUS pelo dom do conhecimento\n";
 Author: Eduardo Marçal\n";
 Create on: 22/08/15\n";
 */
package imagetoassemblemips;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class ImageToAssembleMips {

    private static String Calcular(int cor) {
        String retorno;
        String hex = "";
        if (cor <= 0) {
            return "00";
        }

        int valor = cor / 16, resto = cor % 16;
      //   System.out.println("valor = "+valor+" | resto = "+resto);

        if (cor < 16) {
            retorno = "0" + valor;
            return retorno;
        } else {
            switch (resto) {
                case 1:
                    hex = "1";
                    break;
                case 2:
                    hex = "2";
                    break;
                case 3:
                    hex = "3";
                    break;
                case 4:
                    hex = "4";
                    break;
                case 5:
                    hex = "5";
                    break;
                case 6:
                    hex = "6";
                    break;
                case 7:
                    hex = "7";
                    break;
                case 8:
                    hex = "8";
                    break;
                case 9:
                    hex = "9";
                    break;
                case 10:
                    hex = "A";
                    break;
                case 11:
                    hex = "B";
                    break;
                case 12:
                    hex = "C";
                    break;
                case 13:
                    hex = "D";
                    break;
                case 14:
                    hex = "E";
                    break;
                case 15:
                    hex = "F";
                    break;
            }
            retorno = hex;
            switch (valor) {
                case 1:
                    hex = "1";
                    break;
                case 2:
                    hex = "2";
                    break;
                case 3:
                    hex = "3";
                    break;
                case 4:
                    hex = "4";
                    break;
                case 5:
                    hex = "5";
                    break;
                case 6:
                    hex = "6";
                    break;
                case 7:
                    hex = "7";
                    break;
                case 8:
                    hex = "8";
                    break;
                case 9:
                    hex = "9";
                    break;
                case 10:
                    hex = "A";
                    break;
                case 11:
                    hex = "B";
                    break;
                case 12:
                    hex = "C";
                    break;
                case 13:
                    hex = "D";
                    break;
                case 14:
                    hex = "E";
                    break;
                case 15:
                    hex = "F";
                    break;
            }
            retorno = hex + retorno;
        }

       // System.out.println(retorno);
        return retorno;

    }

    public static void main(String[] args) throws AWTException, URISyntaxException {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Escolhar o sprite");
            int retorno = jFileChooser.showOpenDialog(null);
            
            String path;
               
            if(retorno!=JFileChooser.APPROVE_OPTION) 
            {
               JOptionPane.showMessageDialog(null, "Arquivo invalido!");
                System.exit(0);
            }
            
            path = jFileChooser.getSelectedFile().getCanonicalPath();

            BufferedImage image = ImageIO.read(new File(path));
            int largura = image.getWidth();
            int altura = image.getHeight();          
            System.err.println(largura+""+altura);
          
            // Cabeçalho do código fonte
            String codigo  = "# Thanks: Agradeço a DEUS pelo dom do conhecimento\n";
                   codigo += "# Author: Eduardo Marçal\n";
                   codigo += "# Create on: 22/08/15\n";
                   
                   codigo+="\n.globl Main"
                         + "\n.data"
                         + "\n.text"
                         + "\nMain:\n";
            
            int red, green, blue;
            int corAtual = 0, corAnterior;
            JFrame janela = new JFrame();
            
            JProgressBar jProgressBar = new JProgressBar();
            jProgressBar.setMinimum(0);
            jProgressBar.setMaximum(altura);
            janela.setSize(300,100);
            janela.setLocationRelativeTo(null);
            janela.add(jProgressBar);
            janela.setVisible(true);
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        
            for (int linha = 0; linha < altura; linha++) {
                if(linha==0)
                {
                    
                    codigo += "\t#Posicao Inicial\n";
                    codigo += "\t#Linha: " + String.valueOf(linha + 1) + "\n";
                    codigo += "\tadd $a0,$a0," + 1 + "\t#linha = " + String.valueOf(linha+1) + "\n";
                }
                else
                {
                    codigo += "\n\t#Linha: " + String.valueOf(linha + 1) + "\n";
                    codigo += "\tadd $a0,$a0," + 1 + "\t#linha = " + String.valueOf(linha+1) + "\n";
                }
                jProgressBar.setValue(linha);
                jProgressBar.setStringPainted(true);
                for (int coluna = 0; coluna < largura; coluna++) {
                    
                    corAnterior = corAtual;
                    corAtual = image.getRGB(coluna, linha);
                    
                    red = (corAtual >> 16)  & 0xFF;
                    green = (corAtual >> 8) & 0xFF;
                    blue = (corAtual) & 0xFF;
                     
                    if(linha==0 && coluna==0)
                    {
                        codigo += "\tadd $a1,$a1," + 1 + "\t#coluna = " + String.valueOf(coluna+1) + "\n";
                    } else if(coluna==0)
                    {
                       
                       codigo += "\tsub $a1,$a1," + (largura-1) + "\t#coluna = " + String.valueOf(coluna+1) + "\n";
                    }
                    else{   
                        codigo += "\tadd $a1,$a1," + 1 + "\t#coluna = " + String.valueOf(coluna+1) + "\n";
                    }                                                                             
                    
                    if(corAnterior!=corAtual)
                    {
                        codigo += "\tadd $a2,$zero,0x" + Calcular(red) + Calcular(green) + Calcular(blue) + "\n";
                    }
                    codigo += "\tjal Plot\n";
                
                    System.out.println("linha:" + linha + " | coluna:" + coluna);
                }
                
            }
            
            janela.dispose();
                    
            
            //Rodape
            codigo+="\n" + "j Exit\n" + "\n" + "\n" +
                    "#----------------------------------------------------------------------------------------------# \n" +
                    "\n" + "Plot:\n" + "###################################################################\n" +
                    "# $a0 = linha\n" + "# $a1 = coluna\n" + "# $a2 = cor\n" +
                    "# Formula: p(l,c)(64x256) = (coluna - 1) * 4  + (linha - 1) * 1024\n" +
                    "###################################################################\n" + "	\n" +
                    "	sub $t1,$a1,1       # primeira operaÃ§Ã£o (coluna -1)\n" +
                    "	sub $t0,$a0,1       # segunda operaÃ§Ã£o (linha -1)\n" +
                    "	addi $t2,$zero,4    # constante de multiplicaÃ§Ã£o pixel\n" +
                    "	mult $t1,$t2        # primeira multiplicaÃ§Ã£o\n" +
                    "	mflo $t1            # guarda o resultado da primeira operaÃ§Ã£o\n" +
                    "	addi $t2,$zero,1024  # constante de multiplicaÃ§Ã£o largura\n" +
                    "	mult $t0,$t2        # multiplicaÃ§Ã£o dos operandos\n" +
                    "   mflo $t0            # guarda o resultado da segunda operaÃ§Ã£o\n" +
                    "	add $t0,$t0,$t1     # soma os resultados\n" +
                    "	lui $t1,0x1001      # ler os dados da memÃ³ria\n" +
                    "	add $t1,$t1,$t0     # adiciona o resultado da operaÃ§Ã£o no registrador\n" +
                    "	add $t2,$zero,$a2   # cor recebida\n" +
                    "	sw $t2,0($t1)       # escreve na memÃ³ria a cor recebida\n" +
                    "	jr $ra              # retorna para a proxima instruÃ§Ã£o chamada\n" +"\n" +
                    "#----------------------------------------------------------------------------------------------# \n" +
                    "Exit:\n" + "	addi $v0, $zero, 10\n" + "	syscall\n" +"	\n" +
                    "#----------------------------------------------------------------------------------------------# ";
           //jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            jFileChooser.setDialogTitle("Escolhar o local para salvar o código do sprite");
            jFileChooser.setFileHidingEnabled(false);
            retorno = jFileChooser.showSaveDialog(null);
            String arquivo;
            //File file = jFileChooser.getSelectedFile();
            if(retorno != JFileChooser.APPROVE_OPTION)
            {
                JOptionPane.showMessageDialog(null, "Diretório inválido!");
                System.exit(0);
            }        
           arquivo = jFileChooser.getSelectedFile().getAbsolutePath()+".edu";                
          // if(!arquivo.equals(""))    
           
           
            //= JOptionPane.showInputDialog(null);
           // arquivo = "C:\\Users\\Eduardo\\Dropbox\\IFRN_ANDAMENTO\\2_SEMESTRE\\ARQUITETURA DE COMPUTADORES\\trabalho\\mario_sound\\atest01.edu";
            //try (BufferedWriter bw = new BufferedWriter(new FileWriter("B:\\Documentos\\NetBeansProjects\\ImageToAssembleMips\\src\\image\\smartguy.edu"))) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
                bw.write(codigo);
                bw.close();
                JOptionPane.showMessageDialog(null, "Gravado com sucesso!\n"+arquivo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex + "\nErro de escrita");
            }
        } catch (IOException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, e + "\n Erro na leitura da imagem");
        }
    }
}