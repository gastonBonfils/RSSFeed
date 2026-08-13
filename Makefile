# Compiler options
JAVAC = javac
LIB_DIR = lib
LIBS = $(wildcard $(LIB_DIR)/*.jar)
JFLAGS = -cp "$(LIBS)"

# Source directory
SRCDIR = src

# Output directory
OUTDIR = out

# Java source files
SOURCES = $(shell find src -name "*.java")

# Main class
MAIN = FeedReaderMain

# Default target
default: run

# Target to compile the Java source files
build:
	$(JAVAC) $(JFLAGS) -d $(OUTDIR) $(SOURCES)

# Target to run the application
run: build
	java $(JFLAGS):$(OUTDIR) $(MAIN)

# Target to run the application with the "-ne" flag
heuristic: MAIN += -ne
heuristic: run

# Target to clean the project
clean:
	rm -rf $(OUTDIR)

.PHONY: default build clean

