import java.util.*;
import java.io.*;

public class TextBuddy {

	static File file_object = null;

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

		String fileName;

		Stack<String> record = new Stack<String>();
		Stack<String> extra = new Stack<String>();

		fileName = getFileName(args, sc);
		createOrModify(sc, fileName, record);
		welcomeMsg(fileName);
		commandInput(sc, fileName, record, extra);
		saveData(record, file_object);
		sc.close();
	}

	private static void commandInput(Scanner sc, String fileName,
			Stack<String> record, Stack<String> extra)
			throws FileNotFoundException {
		String command;

		do {
			command = requestInput(sc);

			if (command.equals("display")) {
				displayContent(fileName, record, extra);

			} else if (command.equals("clear")) {
				deleteAll(fileName, record);

			} else if (command.equals("add")) {
				addTask(sc, fileName, record);

			} else if (command.equals("delete")) {
				deleteTask(sc, fileName, record, extra);
			}

		} while (!command.equals("exit"));

	}

	private static void deleteTask(Scanner sc, String fileName,
			Stack<String> record, Stack<String> extra)
			throws FileNotFoundException {
		int deleteNo = sc.nextInt();
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

		saveData(record, file_object);
	}

	private static void addTask(Scanner sc, String fileName,
			Stack<String> record) throws FileNotFoundException {
		String information = sc.nextLine();
		record.push(information.trim());
		System.out.println("added to " + fileName + ": \"" + information.trim()
				+ "\"");
		saveData(record, file_object);
	}

	private static void deleteAll(String fileName, Stack<String> record)
			throws FileNotFoundException {
		while (!record.empty()) {
			record.pop();
		}
		System.out.println("all content deleted from " + fileName);
		saveData(record, file_object);
	}

	private static void displayContent(String fileName, Stack<String> record,
			Stack<String> extra) {
		int displayCounter = 1;
		String display;
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
	}

	private static String requestInput(Scanner sc) {
		String command;
		System.out.print("command: ");
		command = sc.next();
		return command;
	}

	private static void welcomeMsg(String fileName) {
		System.out.println("Welcome to TextBuddy. " + fileName
				+ " is ready for use");
	}

	private static void createOrModify(Scanner sc, String fileName,
			Stack<String> record) throws IOException {
		if (!file_object.exists()) {
			file_object.createNewFile();
			System.out.print(fileName + " has been created \n");
		} else if (file_object.exists()) {
			sc.nextLine();
			System.out.print("Would you like to edit file? (Y/N)");
			String choice = sc.nextLine();
			if (choice.equalsIgnoreCase("N")) {
				System.exit(0);
			} else if (choice.equalsIgnoreCase("Y")) {
				String[] previous = oldData(file_object);
				for (int i = 0; i < previous.length; i++) {
					record.push(previous[i]);
				}
			}

		}
	}

	private static String getFileName(String[] args, Scanner sc) {
		String fileName;
		// If no arguments, get arguments, for regression testing
		if (args.length < 1) {
			fileName = sc.next();
			file_object = new File(fileName);
		} else {
			fileName = args[0];
			file_object = new File(fileName);
		}
		return fileName;
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