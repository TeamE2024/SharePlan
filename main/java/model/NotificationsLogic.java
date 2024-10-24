package model;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

public class NotificationsLogic {

	public void run() {
		SystemTray tray = SystemTray.getSystemTray(); // システムトレイを取得

		PopupMenu popup = new PopupMenu(); // ※4 ポップアップメニューを生成
		Image image = Toolkit.getDefaultToolkit().createImage(ClassLoader.getSystemResource("C;\\icon.png")); // アイコン画像を準備
		TrayIcon icon = new TrayIcon(image, "Sample Java App", popup); // ※4 トレイアイコンとして生成
		icon.setImageAutoSize(true); // リサイズ

		// ※3 ポップアップメニューの中身を作成
		MenuItem item1 = new MenuItem("sharePlan");
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//	            url
				try {
		            URI uri = new URI("http://localhost:8080/sharePlan_y/Schedule");
		            if (Desktop.isDesktopSupported()) {
		                Desktop.getDesktop().browse(uri);
		            } else {
		                System.out.println("Desktop is not supported.");
		            }
		        } catch (Exception E) {
		            E.printStackTrace();
		        }
			}

		});

		MenuItem item2 = new MenuItem("Exit");
		item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tray.remove(icon);
				// System.exit(0);
			}
		});
		popup.add(item1); // ※4
		popup.add(item2); // ※4

		try {
			SystemTray.getSystemTray().add(icon); // ※2 システムトレイに追加
			icon.displayMessage("sharePlan", "スケジュールが更新されました", MessageType.INFO);

		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
