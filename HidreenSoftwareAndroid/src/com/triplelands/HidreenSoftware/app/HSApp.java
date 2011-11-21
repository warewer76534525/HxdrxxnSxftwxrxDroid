package com.triplelands.HidreenSoftware.app;

import java.util.List;

import com.google.inject.Module;

import roboguice.application.RoboApplication;

public class HSApp extends RoboApplication {
	@Override
	protected void addApplicationModules(List<Module> modules) {
		modules.add(new HSModule());
	}
}
