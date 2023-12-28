#include <stdio.h>
#include <stdint.h>

#ifdef _MSC_VER
// MSVC-compatible declaration
extern int64_t our_code_starts_here();
#else
// GCC-compatible declaration
extern int64_t our_code_starts_here() asm("our_code_starts_here");
#endif

int main(int argc, char** argv) {
  int64_t result = our_code_starts_here();
  printf("%lld\n", result);
  return 0;
}
