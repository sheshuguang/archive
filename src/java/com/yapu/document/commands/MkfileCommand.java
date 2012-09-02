package com.yapu.document.commands;

import com.yapu.document.ConnectorException;
import com.yapu.document.FileActionEnum;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.Writer;

/**
 * @author Antoine Walter (www.anw.fr)
 * @date 29 aug. 2011
 * @version $Id$
 * @license BSD
 */
public class MkfileCommand extends AbstractCommandOverride {
	private static Logger logger = Logger.getLogger(MkfileCommand.class);

	@Override
	public void execute() throws ConnectorException {
		File dirCurrent = getExistingDir(getParam("current"), FileActionEnum.CREATE_FILE);
		if (dirCurrent != null) {
			File newFile = getNewFile(getParam("name"), dirCurrent, FileActionEnum.WRITE);

			Writer out = null;
			try {
				// write with empty data
				getFs().createFile(newFile, null);
			} catch (Exception e) {
				logger.error("", e);
				throw new ConnectorException("Unable to create folder");
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					logger.error("", e);
				}
			}

			putResponse("select", _hash(newFile));

			_content(dirCurrent, true);
		}
	}
}
