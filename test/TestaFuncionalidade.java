

import br.com.inso.wsgedmobileserver.gedmanager.GedManager;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Glauber
 */
public class TestaFuncionalidade {
    
    public static void main(String[] args)
    {
        GedManager oper = new GedManager();
        
        byte[] retorno = oper.WsObterDocumentoGed("e:\\GEDTESTEJAVA\\gedstor\\000\\000\\026\\000000001.001");
        
        
        
        String conteudo = oper.WsGravarDocumentoGed("c:\\lixo\\teste.txt","TESTE DE GRAVAÇÃO DE ARQUIVO".getBytes());
        
        
    }
    
}
