NASMFLAGS := -f win64
CLANGFLAGS := -g -m64

# Rule to generate .exe or binary file
%.exe: %.o
	clang $(CLANGFLAGS) -o build/bin/$@ src/main/c/main.c build/obj/$<

# Rule to generate .o from .s
%.o: %.s
	nasm $(NASMFLAGS) -o build/obj/$@ build/lib/$<

# Rule to generate .s from .int
%.s: %.int
	sbt --error "run $<" > build/lib/$@
