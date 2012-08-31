package com.yapu.document.commands;

import com.yapu.document.ConnectorException;
import com.yapu.document.FileActionEnum;

import java.io.File;

/**
 * @author Antoine Walter (www.anw.fr)
 * @date 29 aug. 2011
 * @version $Id$
 * @license BSD
 */
public class MkdirCommand extends AbstractCommandOverride {

	@Override
	public void execute() throws ConnectorException {
		File dirCurrent = getExistingDir(getParam("current"), FileActionEnum.CREATE_DIR);
		if (dirCurrent != null) {
			File newDir = getNewFile(getParam("name"), dirCurrent, FileActionEnum.WRITE);

			try {
				getFs().createFolder(newDir);
			} catch (Exception e) {
				throw new ConnectorException("Unable to create folder");
			}

			putResponse("select", _hash(newDir));

			_content(dirCurrent, true);
		}
	}
}
