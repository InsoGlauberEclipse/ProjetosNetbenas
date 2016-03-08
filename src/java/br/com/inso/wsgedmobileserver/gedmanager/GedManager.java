/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.inso.wsgedmobileserver.gedmanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author glauber.duma
 */
@WebService(serviceName = "GedManager")
public class GedManager {
    
//    private IOperacoes wsconsumer;

    /**
     * This is a sample web service operation
     * @param user
     * @return 
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name="user") String user) {
        return "WebService em operação. " ;
    }
    
    /**
     *
     * @param arquivo
     * @param conteudo
     * @return     
     */
    @WebMethod (operationName = "WsGravarDocumentoGed")
    public String WsGravarDocumentoGed(@WebParam(name="arquivo") String arquivo, @WebParam(name="conteudo")byte[] conteudo) 
    {
        
        if (null == arquivo || null == conteudo)
        {
            return "ERRO: Nome ou conteudo do arquivo invalido.";
        }
        
        String SO = System.getProperty("os.name").toUpperCase();
        
        boolean ehWindows = SO.contains("WINDOWS");
        
        String separadorDir = ehWindows ? "\\" : "/";
        
        String nomedir = arquivo.substring(0,(arquivo.lastIndexOf(separadorDir)));
        
        File operArq = new File(nomedir);
        
        boolean existe = operArq.exists();
        
        boolean criou = operArq.mkdirs();
        
        if (existe || criou)
        {
        
            operArq = new File(arquivo);
            try (OutputStream os = new FileOutputStream(operArq)) {
                os.write(conteudo);
                os.flush();
            }
            catch(Exception Ex)
            {
               return "ERRO: Nao foi possivel criar o arquivo:" + arquivo + "["+Ex.getMessage()+"]";             
            }
        }
        else
        {
            return "ERRO: Nao foi possivel criar o diretorio:" + nomedir;            
        }
        return "OK";
        
        
    }
    
    /**
     * Recupera o conteúdo de um documento ged.
     * @param arquivo Nome do arquivo com a localização completa.
     * @return Conteúdo binário do arquivo.
     */
    @WebMethod (operationName = "WsObterDocumentoGed")
    public byte[] WsObterDocumentoGed(@WebParam(name="arquivo") String arquivo)
    {
            
        byte[] retorno = null;
        InputStream entrada;

        File operArq = new File(arquivo);
        boolean existe = operArq.isFile();
        int tamanhoArquivo = (int) operArq.length();
        
        if (existe)
        {
            try {
                entrada = new FileInputStream(arquivo);
                retorno = new byte[tamanhoArquivo];
                int lidos = entrada.read(retorno, 0, tamanhoArquivo);

                if (lidos != tamanhoArquivo) {
                    return "ERRO: Quantidade de bytes lidos não confere com o tamanho do arquivo.".getBytes();
                }

            } catch (FileNotFoundException ex) {
                return  ("ERRO: Não foi encontrado o arquivo:" + arquivo).getBytes();
                

            } catch (IOException ex) {
                return ("ERRO: " + ex.getMessage()).getBytes();               

            }
            
        }
        
        return retorno;

        
    }
    
}
