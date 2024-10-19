package eapli.base.app.user.console.test;

import eapli.base.message.application.ProcessMessageController;

import java.time.LocalDateTime;

public class ProcessMessageControllerTest {

    public static void main(String[] args) {
        ProcessMessageController processMessageController = new ProcessMessageController();
        /*test mode 1
        processMessageController.processRawMessages(LocalDateTime.now().plusMinutes(1),
                LocalDateTime.now().plusMinutes(2), 0);
         */

        /*test mode 2*/
        /*processMessageController.processRawMessages(LocalDateTime.now().plusMinutes(1),
                null, 1);*/
    }
}
