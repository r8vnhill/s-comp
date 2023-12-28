#include <stdio.h>
#include <stdint.h>
#include <inttypes.h>

#ifdef _MSC_VER
// MSVC-compatible declaration
// No special decoration is needed for MSVC
extern int64_t our_code_starts_here();
#else
// GCC-compatible declaration
// The asm directive is used to specify the symbol name in the assembly output
extern int64_t our_code_starts_here() asm("our_code_starts_here");
#endif

int main(int argc, char** argv) {
  int64_t result = our_code_starts_here();
  // Using PRId64 to ensure portable format specifier for int64_t
  printf("%" PRId64 "\n", result);
  return 0;
}
