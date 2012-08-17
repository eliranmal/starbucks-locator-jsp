package com.starbucks.locator.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.model.dto.Transferable;

public interface AsyncCommand {

	Transferable handle(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException;
}
