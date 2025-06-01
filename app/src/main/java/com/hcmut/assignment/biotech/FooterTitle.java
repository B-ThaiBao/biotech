package com.hcmut.assignment.biotech;

import android.widget.TextView;

import java.util.Stack;

public class FooterTitle {
	public static TextView value;
	public static Stack<String> stk = new Stack<>();

	public static void pushBack(String title) {
		value.setText(title);
		stk.push(title);
	}
	public static void popBack() {
		stk.pop();
		if (!stk.empty()) value.setText(stk.peek());
	}
}
