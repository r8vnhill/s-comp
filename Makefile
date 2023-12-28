# Detect the operating system
ifeq ($(OS),Windows_NT)
    detected_OS := Windows
else
    detected_OS := $(shell uname -s)
endif

# Set platform-specific variables
ifeq ($(detected_OS),Linux)
    NASMFLAGS := -f elf64
    CLANGFLAGS := -g -m64
    EXE_EXT := 
endif
ifeq ($(detected_OS),Darwin) # macOS is identified by 'Darwin'
    NASMFLAGS := -f macho64
    CLANGFLAGS := -g -m64
    EXE_EXT := 
endif
ifeq ($(detected_OS),Windows)
    NASMFLAGS := -f win64
    CLANGFLAGS := -g -m64
    EXE_EXT := .exe
endif

# Rule to generate .exe or binary file
%$(EXE_EXT): %.o
	clang $(CLANGFLAGS) -o build/bin/$@ src/main/c/main.c $<

# Rule to generate .o from .s
%.o: %.s
	nasm $(NASMFLAGS) -o build/s/$@ $<

# Rule to generate .s from .int
%.s: %.int
	sbt --error "run $<" > $@
