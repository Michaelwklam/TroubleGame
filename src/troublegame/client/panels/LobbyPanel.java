package troublegame.client.panels;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

import troublegame.client.Interface;
import troublegame.client.SwingUI;
import troublegame.communication.CommunicationHandler;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;

public class LobbyPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5762982387707435312L;
	
	/**
	 * The swing user interface.
	 */
	private SwingUI swingUI;
	
	/**
	 * The Default List Model for JList of Users
	 */
	private DefaultListModel<String> gameRoomModel;
	
	/**
	 * The background image.
	 */
	private Image backgroundImage;
	
	/**
	 * The constructor for the Lobby panel.
	 * @param swingUI is the swing user interface.
	 */
	public LobbyPanel(SwingUI swingUI) {
		this.swingUI = swingUI;
		
		/**
		 * set background image
		 */
		try {
			backgroundImage = ImageIO.read(new File("./data/img/background2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.init();
		refreshGameRooms();	

	}

	private JTextArea chatMessages;
	private DefaultListModel<String> onlineUserlistModel;
	private DefaultListModel<String> activityFeedModel;
	
	/**
	 * Create the panel.
	 */
	public void init() {
		
		setLayout(null);
		String myUsername = swingUI.getUser().getUsername();
		
		Image image1 = Toolkit.getDefaultToolkit().getImage("./data/img/button_1x.png");
		Image image2 = Toolkit.getDefaultToolkit().getImage("./data/img/button_3.png");
		Image newimg1 = image1.getScaledInstance(114, 23, Image.SCALE_SMOOTH);
		Image newimg2 = image2.getScaledInstance(114, 23, Image.SCALE_SMOOTH);
		ImageIcon imgIcon1 = new ImageIcon(newimg1);
		ImageIcon imgIcon2 = new ImageIcon(newimg2);
		
		gameRoomModel = new DefaultListModel<String>();
		onlineUserlistModel = new DefaultListModel<String>();
		activityFeedModel = new DefaultListModel<String>();
		
		JList<String> gameList = new JList<String>(gameRoomModel);
		gameList.setBounds(43, 67, 553, 196);
		this.add(gameList);
		
		JButton createButton = new JButton("Create Game");
		createButton.setBounds(366, 263, 114, 25);
		this.add(createButton);
		
		createButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.GAME_ROOM_NEW);
			}
		});
		createButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				createButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				createButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		createButton.setIcon(imgIcon1);
		createButton.setHorizontalTextPosition(SwingConstants.CENTER);
		createButton.setBorderPainted(false);
		
		JButton joinButton = new JButton("Join");
		joinButton.setBounds(482, 263, 114, 25);
		this.add(joinButton);
		joinButton.setEnabled(false);	
		joinButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.GAME_ROOM_JOIN + " " + gameList.getSelectedValue());
			}
		});
		joinButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				joinButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				joinButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		joinButton.setIcon(imgIcon1);
		joinButton.setHorizontalTextPosition(SwingConstants.CENTER);
		joinButton.setBorderPainted(false);
		gameList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!joinButton.isEnabled()) joinButton.setEnabled(true);
			}
		});
		
		JButton profileButton = new JButton("My Profile");
		profileButton.setBounds(695, 13, 114, 25);
		this.add(profileButton);
		
		profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.setInterface(Interface.USER_PROFILE);
			}
		});
		profileButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				profileButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				profileButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		profileButton.setIcon(imgIcon1);
		profileButton.setHorizontalTextPosition(SwingConstants.CENTER);
		profileButton.setBorderPainted(false);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(810, 13, 114, 25);
		this.add(logoutButton);
		
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				int confirmed = JOptionPane.showConfirmDialog(null, 
						"Are you sure you want to exit the game?", "Exit Trouble Message",
						JOptionPane.YES_NO_OPTION);
					if (confirmed == JOptionPane.YES_OPTION)
						swingUI.send(CommunicationHandler.LOGOUT_REQUEST);
			}
		});
		logoutButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				logoutButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				logoutButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		logoutButton.setIcon(imgIcon1);
		logoutButton.setHorizontalTextPosition(SwingConstants.CENTER);
		logoutButton.setBorderPainted(false);
		
		JButton addFriendButton = new JButton("Add Friend");
		addFriendButton.setBounds(810, 263, 114, 25);
		addFriendButton.setVisible(false);
		add(addFriendButton);
		
		addFriendButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				addFriendButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				addFriendButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		addFriendButton.setIcon(imgIcon1);
		addFriendButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addFriendButton.setBorderPainted(false);
		
		
		JList<String> onlineUsers = new JList<String>(onlineUserlistModel);
		onlineUsers.setBounds(615, 67, 309, 196);
		this.add(onlineUsers);
		
		onlineUsers.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (onlineUsers.getSelectedValue().equals(myUsername)) {
					addFriendButton.setVisible(false);
					return;
				}
				if (!addFriendButton.isVisible()) addFriendButton.setVisible(true);
			}
		});
		
		addFriendButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				swingUI.send(CommunicationHandler.FRIEND_ADD_ATTEMPT + " " + onlineUsers.getSelectedValue());
			}
		});
		
		JLabel lblGameRooms = new JLabel("Game Rooms");
		lblGameRooms.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGameRooms.setLabelFor(gameList);
		lblGameRooms.setBounds(43, 46, 106, 16);
		this.add(lblGameRooms);
		
		JLabel lblUsersOnline = new JLabel("Users in Lobby");
		lblUsersOnline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsersOnline.setLabelFor(onlineUsers);
		lblUsersOnline.setBounds(616, 46, 114, 16);
		this.add(lblUsersOnline);
		
		JPanel panel = new JPanel();
		panel.setBounds(440, 318, 484, 196);
		chatMessages = new JTextArea();
		DefaultCaret caret = (DefaultCaret)chatMessages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		chatMessages.setLineWrap(true);
		//chatMessages.setBounds(103, 220, 503, 151);
		JScrollPane scroll = new JScrollPane(chatMessages);
		chatMessages.setEditable(false);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		panel.add(scroll);
		this.add(panel);
		
		//chatMessages = new JTextArea();
		//chatMessages.setBounds(440, 318, 484, 196);
		//this.add(chatMessages);
		
		JLabel lblChat = new JLabel("Chat");
		lblChat.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblChat.setLabelFor(chatMessages);
		lblChat.setBounds(440, 296, 56, 16);
		this.add(lblChat);
		
		JTextField newMessage = new JTextField();
		newMessage.setBounds(440, 516, 367, 24);
		this.add(newMessage);
		newMessage.setColumns(10);
		
		JButton sendChatButton = new JButton("Send");
		sendChatButton.setBounds(810, 516, 114, 25);
		this.add(sendChatButton);

		newMessage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage(newMessage);	
			}
		});
		
		sendChatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				sendChatMessage(newMessage);	
			}
		});
		sendChatButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				sendChatButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				sendChatButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		sendChatButton.setIcon(imgIcon1);
		sendChatButton.setHorizontalTextPosition(SwingConstants.CENTER);
		sendChatButton.setBorderPainted(false);
		
		JList<String> activityFeed = new JList<String>(activityFeedModel);
		activityFeed.setBounds(43, 318, 376, 222);
		activityFeed.setEnabled(false);
		this.add(activityFeed);
		
		JLabel lblActivity = new JLabel("Activity Feed");
		lblActivity.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblActivity.setLabelFor(activityFeed);
		lblActivity.setBounds(43, 296, 106, 16);
		this.add(lblActivity);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setToolTipText("Refresh if your friend's room is not appearing!");
		refreshButton.setBounds(482, 42, 114, 25);
		add(refreshButton);
		
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				swingUI.playButtonSound();
				refreshGameRooms();	
			}
		});
		refreshButton.addMouseListener(new MouseListener() {			
			@Override
			public void mouseReleased(MouseEvent arg0) {}		   
			@Override
			public void mousePressed(MouseEvent arg0) {}			
			@Override
			public void mouseExited(MouseEvent arg0) { 
				refreshButton.setIcon(imgIcon1);
			}		   
			@Override
			public void mouseEntered(MouseEvent arg0) {
				refreshButton.setIcon(imgIcon2);
			}		   
			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});		
		refreshButton.setIcon(imgIcon1);
		refreshButton.setHorizontalTextPosition(SwingConstants.CENTER);
		refreshButton.setBorderPainted(false);
		
		JLabel usernameLabel = new JLabel("You are logged in as: "+myUsername);
		usernameLabel.setBounds(43, 17, 394, 16);
		add(usernameLabel);
		
		
		
	}
	
	/**
	 * @return the swing user interface.
	 */
	public SwingUI getSwingUI() {
		return swingUI;
	}

	/**
	 * Handles the chat message by reading the text field
	 * @param textField is the {@link JTextField} in the game room.
	 */
	private void sendChatMessage(JTextField textField) {
		String toSend = textField.getText();
		if(toSend.equals("")) return;
		swingUI.send(CommunicationHandler.LOBBY_CHAT + " " + toSend);
		textField.setText("");	
	}
	
	/**
	 * Sets the swing user interface.
	 * @param swingUI is the swing user interface.
	 */
	public void setSwingUI(SwingUI swingUI) {
		this.swingUI = swingUI;
	}
	
	/**
	 * queries server for current game rooms
	 * @param username
	 */
	public void refreshGameRooms() {
		swingUI.send(CommunicationHandler.GAME_ROOM_QUERY);
	}
	
	public void addGameRoom(String username) {
		if (gameRoomModel.contains(username)) return;
		gameRoomModel.addElement(username);
	}
	
	public void removeGameRoom(String gameRoomName) {
		gameRoomModel.removeElement(gameRoomName);
	}
	
	public void clearGameRooms() {
		gameRoomModel.clear();
	}
	
	public void updateChat(String message) {
		chatMessages.append(message+"\n");
	}
	
	public void addActivityFeed(String feed) {
		activityFeedModel.addElement(feed);
	}
	
	public void clearActivityFeed() {
		activityFeedModel.clear();
	}
	/**
	 * Updates the online list
	 * @param array of strings containing usernames
	 */
	public void updateOnlineList(List<String> users) {
		// first we look for the different users logged in or out
		String myUsername = swingUI.getUser().getUsername();
		
		for (String user: users) {
			if (!onlineUserlistModel.contains(user) && !myUsername.equals(user) && !onlineUserlistModel.isEmpty()) {
				addActivityFeed(user+ " has logged in.");
			}
		}
		
		while (!onlineUserlistModel.isEmpty()) {
			String user = onlineUserlistModel.remove(0);
			if (!users.contains(user)) {
				addActivityFeed(user+" has logged out.");
			}
		}

		for (String user: users) {		
			onlineUserlistModel.addElement(user);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	
		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, this);
	}
}
