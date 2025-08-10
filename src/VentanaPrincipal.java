/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ibane
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    private java.io.File currentFile = null;
    
        private String procesarTexto() {
        String texto = txtArea.getText();
        if (texto == null || texto.trim().isEmpty()) {
            return "No hay texto para procesar.";
        }

        int totalCaracteres = texto.length();
        int totalSinEspacios = texto.replaceAll("\\s+","").length();

        String normal = texto.toLowerCase().replaceAll("[^\\p{L}\\p{Nd}\\s]+", " ");
        String[] palabrasArr = normal.trim().isEmpty() ? new String[0] : normal.trim().split("\\s+");
        int totalPalabras = palabrasArr.length;

        java.util.Map<String,Integer> freq = new java.util.HashMap<>();
        for (String w : palabrasArr) {
            if (w.isEmpty()) continue;
            freq.put(w, freq.getOrDefault(w, 0) + 1);
        }
        java.util.List<java.util.Map.Entry<String,Integer>> orden = new java.util.ArrayList<>(freq.entrySet());
        orden.sort((a,b) -> b.getValue().compareTo(a.getValue()));

        int vocales = 0, consonantes = 0, digitos = 0;
        String vocalesStr = "aeiouáéíóúü";
        for (char c : texto.toLowerCase().toCharArray()) {
            if (Character.isDigit(c)) digitos++;
            else if (Character.isLetter(c)) {
                if (vocalesStr.indexOf(c) >= 0) vocales++;
                else consonantes++;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Estadísticas:\n");
        sb.append("Caracteres (total): ").append(totalCaracteres).append("\n");
        sb.append("Caracteres (sin espacios): ").append(totalSinEspacios).append("\n");
        sb.append("Palabras (total): ").append(totalPalabras).append("\n");
        sb.append("Vocales: ").append(vocales).append("\n");
        sb.append("Consonantes: ").append(consonantes).append("\n");
        sb.append("Dígitos: ").append(digitos).append("\n\n");

        sb.append("Palabras más frecuentes:\n");
        int top = Math.min(10, orden.size());
        for (int i = 0; i < top; i++) {
            var e = orden.get(i);
            sb.append((i+1)).append(". ").append(e.getKey()).append(" -> ").append(e.getValue()).append("\n");
        }
        return sb.toString();
    }

    private String traducirMurcielago(String texto) {
        String clave = "murcielago";
        String numeros = "0123456789";
        StringBuilder out = new StringBuilder();
        for (char ch : texto.toLowerCase().toCharArray()) {
            int idx = clave.indexOf(ch);
            if (idx >= 0) out.append(numeros.charAt(idx));
            else out.append(ch);
        }
        return out.toString();
    }

    private void abrirArchivo() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int res = fc.showOpenDialog(this);
        if (res == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File f = fc.getSelectedFile();
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(f.toPath());
                String contenido = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
                txtArea.setText(contenido);
                currentFile = f;
            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al leer: " + ex.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void guardarArchivo() {
        if (currentFile != null) {
            try {
                java.nio.file.Files.write(currentFile.toPath(),
                        txtArea.getText().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                javax.swing.JOptionPane.showMessageDialog(this, "Archivo guardado.");
            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            guardarArchivoComo();
        }
    }

    private void guardarArchivoComo() {
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        int res = fc.showSaveDialog(this);
        if (res == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File f = fc.getSelectedFile();
            try {
                java.nio.file.Files.write(f.toPath(),
                        txtArea.getText().getBytes(java.nio.charset.StandardCharsets.UTF_8));
                currentFile = f;
                javax.swing.JOptionPane.showMessageDialog(this, "Archivo guardado.");
            } catch (java.io.IOException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(),
                        "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarTextoEnArea(String buscar) {
        if (buscar == null || buscar.isEmpty()) return;
        javax.swing.text.Highlighter high = txtArea.getHighlighter();
        high.removeAllHighlights();
        String contenido = txtArea.getText().toLowerCase();
        String target = buscar.toLowerCase();
        int index = 0;
        javax.swing.text.DefaultHighlighter.DefaultHighlightPainter painter =
                new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(java.awt.Color.YELLOW);
        try {
            while ((index = contenido.indexOf(target, index)) >= 0) {
                high.addHighlight(index, index + target.length(), painter);
                index += target.length();
            }
            if (high.getHighlights().length == 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "No se encontró la palabra.");
            }
        } catch (javax.swing.text.BadLocationException ble) {
            // ignore
        }
    }

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());

    /**
     * Creates new form form
     */
    public VentanaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        btnProcesar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuGuardarComo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuCopiar = new javax.swing.JMenuItem();
        menuCortar = new javax.swing.JMenuItem();
        menuPegar = new javax.swing.JMenuItem();
        menuBuscar = new javax.swing.JMenuItem();
        menuReemplazar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtArea.setColumns(20);
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        btnProcesar.setText("Procesar");
        btnProcesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcesarActionPerformed(evt);
            }
        });

        resultArea.setEditable(false);
        resultArea.setColumns(20);
        resultArea.setRows(5);
        jScrollPane2.setViewportView(resultArea);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Manejo de cadenas");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Clave murcielago");

        jMenu1.setText("Archivo");

        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAbrirActionPerformed(evt);
            }
        });
        jMenu1.add(menuAbrir);

        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarActionPerformed(evt);
            }
        });
        jMenu1.add(menuGuardar);

        menuGuardarComo.setText("Guardar Como");
        menuGuardarComo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuGuardarComoActionPerformed(evt);
            }
        });
        jMenu1.add(menuGuardarComo);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Editar");

        menuCopiar.setText("Copiar");
        jMenu2.add(menuCopiar);

        menuCortar.setText("Cortar");
        jMenu2.add(menuCortar);

        menuPegar.setText("Pegar");
        jMenu2.add(menuPegar);

        menuBuscar.setText("Buscar");
        jMenu2.add(menuBuscar);

        menuReemplazar.setText("Reemplazar");
        jMenu2.add(menuReemplazar);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(btnProcesar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(jLabel2)))
                .addContainerGap(146, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(btnProcesar)
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        abrirArchivo();
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void menuGuardarComoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarComoActionPerformed
        guardarArchivoComo();
    }//GEN-LAST:event_menuGuardarComoActionPerformed

    private void btnProcesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarActionPerformed
         String resumen = procesarTexto();
    resultArea.setText(resumen);
    String traduccion = traducirMurcielago(txtArea.getText());
    resultArea.append("\n\nTraducción a clave Murciélago:\n" + traduccion);
    }//GEN-LAST:event_btnProcesarActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        guardarArchivo();
    }//GEN-LAST:event_menuGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProcesar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuBuscar;
    private javax.swing.JMenuItem menuCopiar;
    private javax.swing.JMenuItem menuCortar;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuGuardarComo;
    private javax.swing.JMenuItem menuPegar;
    private javax.swing.JMenuItem menuReemplazar;
    private javax.swing.JTextArea resultArea;
    private javax.swing.JTextArea txtArea;
    // End of variables declaration//GEN-END:variables
}
