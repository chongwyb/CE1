import java.util.*;
import java.io.*;

public class TextBuddy {

	static File file_object = null;

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);
		String command = new String();
		String display = new String();
		String fileName;
		int displayCounter = 1;
		int deleteNo;
		Stack<String> record = new Stack<String>();
		Stack<String> extra = new Stack<String>();

		// If no arguments, get arguments, for regression testing
		if (args.length < 1) {
			fileName = sc.next();
			file_object = new File(fileName);
		} else {
			fileName = args[0];
			file_object = new File(fileName);
		}
		
		// Create (or modify) new file
		if (!file_object.exists()) {
			file_object.createNewFile();
			System.out.print(fileName + " has been created \n");
		} else if (file_object.exists()) {
			System.out.print("Would you like to edit file? (Y/N)");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("N")) {
				System.exit(0);
			} else if (choice.equalsIgnoreCase("Y")) {
				// returns array need convert to stack
				String[] previous = oldData(file_object);
				for (int i = 0; i < previous.length; i++) {
					record.push(previous[i]);
				}
			}

		}

		// Display Welcome Message
		System.out.println("Welcome to TextBuddy. " + fileName
				+ " is ready for use");

		// Loop for editing
		do {
			// Request Input
			System.out.print("command: ");
			command = sc.next();

			if (command.equals("display")) {
				// System.out.println("display");
				if (record.empty()) {
					System.out.println(fileName + " is empty");
				} else {
					while (!record.empty()) {
						extra.push(record.pop());
					}
					while (!extra.empty()) {
						display = extra.pop();
						System.out.println(displayCounter + ". " + display);
						record.push(display);
						displayCounter++;
					}
					displayCounter = 1;
				}

			} else if (command.equals("clear")) {
				// System.out.println("clear");
				while (!record.empty()) {
					record.pop();
				}
				System.out.println("all content deleted from " + fileName);

			} else if (command.equals("add")) {
				// System.out.println("add");
				command = sc.nextLine();
				record.push(command.trim());
				System.out.println("added to " + fileName + ": \""
						+ command.trim() + "\"");

			} else if (command.equals("delete")) {
				// System.out.println("delete");
				deleteNo = sc.nextInt();
				if (record.empty() || record.size() < deleteNo) {
					System.out.println("Unable to delete");
				} else {
					while (deleteNo != record.size()) {
						extra.push(record.pop());
					}
					System.out.println("deleted from " + fileName + ": \""
							+ record.pop() + "\"");
					while (!extra.empty()) {
						record.push(extra.pop());
					}
				}

				/*
				 * } else if(command.equals("size")){
				 * System.out.println(record.size());
				 */
			}

		} while (!command.equals("exit"));

		saveData(record, file_object);

		sc.close();
	}

	private static void saveData(Stack<String> stk, File file_object)
			throws FileNotFoundException {
		PrintWriter writer2 = new PrintWriter(file_object);
		String[] strarr = new String[stk.size()];
		stk.toArray(strarr);
		for (int i = 0; i < strarr.length; i++) {
			writer2.println(strarr[i]);
		}
		writer2.close();

	}

	private static String[] oldData(File file_object) throws IOException {
		FileReader fr = new FileReader(file_object);
		BufferedReader textReader = new BufferedReader(fr);
		int numberOfLines = readLines(file_object);
		String[] textData = new String[numberOfLines];
		for (int i = 0; i < numberOfLines; i++) {
			textData[i] = textReader.readLine();
		}
		textReader.close();
		return textData;
	}

	private static int readLines(File file_object) throws IOException {
		FileReader file_to_read = new FileReader(file_object);
		BufferedReader bf = new BufferedReader(file_to_read);
		String aLine;
		int numberOfLines = 0;
		while ((aLine = bf.readLine()) != null) {
			numberOfLines++;
		}
		bf.close();
		return numberOfLines;

	}
}