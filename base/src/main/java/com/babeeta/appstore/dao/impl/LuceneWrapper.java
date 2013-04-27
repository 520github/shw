package com.babeeta.appstore.dao.impl;

import java.io.File;
import java.io.IOException;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneWrapper {

	public static final String INDEX_DIR = "/var/lib/android-coral-bay/index";

	private Directory directory;

	private final Analyzer paodingAnalyzer = new PaodingAnalyzer();
	private IndexSearcher searcher;
	private IndexWriter writer;

	public LuceneWrapper() {
		setupDirectory();
	}

	public void close() {
		if (writer != null) {
			try {
				writer.close();
				writer = null;
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (searcher != null) {
			try {
				searcher.close();
				searcher = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Directory getDirectory() {
		return directory;
	}

	public Analyzer getPaodingAnalyzer() {
		return paodingAnalyzer;
	}

	public synchronized IndexWriter getWriter() {
		if (writer == null) {
			setupWriter();
		}
		return writer;
	}

	private void setupDirectory() {
		File index = new File(INDEX_DIR);
		index.mkdirs();
		try {
			directory = FSDirectory.open(index);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupWriter() {
		try {
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
					Version.LUCENE_32, paodingAnalyzer);
			indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
			writer = new IndexWriter(directory, indexWriterConfig);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
