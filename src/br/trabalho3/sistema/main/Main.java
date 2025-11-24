package br.trabalho3.sistema.main;

import br.trabalho3.sistema.ui.TelaPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal que inicia o Sistema de Gerenciamento de Academia.
 *

 *
 */
public class Main {

    /**
     * Método main - ponto de entrada da aplicação.
     *
     * Este método:
     * 1. Configura o Look and Feel do sistema operacional
     * 2. Cria e exibe a tela principal do sistema
     * 3. Usa SwingUtilities.invokeLater() para garantir que a
     *    interface seja criada na Event Dispatch Thread (EDT)
     *
     * @param args Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // Exibe mensagem de boas-vindas no console
        exibirBoasVindas();

        // Executa a criação da interface na thread de eventos do Swing (EDT)
        // Isso é uma boa prática para aplicações Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Define o Look and Feel para o padrão do sistema operacional
                    // Faz a aplicação ter aparência nativa (Windows, Mac, Linux)
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                } catch (Exception e) {
                    // Se falhar, usa o Look and Feel padrão do Java (Metal)
                    System.err.println("Não foi possível definir o Look and Feel do sistema.");
                    System.err.println("Usando o Look and Feel padrão.");
                }

                // Cria e exibe a tela principal
                TelaPrincipal telaPrincipal = new TelaPrincipal();
                telaPrincipal.setVisible(true);
            }
        });
    }

    /**
     * Exibe mensagem de boas-vindas e informações do sistema no console.
     */
    private static void exibirBoasVindas() {
        System.out.println("============================================");
        System.out.println("  SISTEMA DE ACADEMIA");
        System.out.println("============================================");
        System.out.println("Instituição: FAESA");
        System.out.println("Disciplina: LPOO");
        System.out.println("============================================\n");

        System.out.println("Iniciando sistema...\n");
    }
}
