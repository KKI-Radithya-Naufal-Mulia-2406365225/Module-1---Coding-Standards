# Module 1

### Reflection 1

I learned about Clean Code and Secure Coding.
For Clean Code, I think the most important thing is naming. Before, I usually name variable like `n` or `p`, but now I use `productName` or `quantity` so other people can understand my code easily. I also try to separate the function so one function only do one thing. 
For Secure Coding, I use UUID for the ID. I think this is better than simple number 1, 2, 3 because it is random and very long. If we use simple number, people can guess the product ID easily.

### Reflection 2

1. After writing the unit test, I feel more confident with my code. I don't know exactly how many tests we need, but I think as long as we check the positive case and negative case, it is enough.
   About code coverage, having 100% coverage is good, but it does not mean my code has no bugs. It only means all lines of code are executed. If I write the logic wrong or forget some weird input, the program can still error even if coverage is 100%.

2. If I create a new functional test and just copy paste the code from the old one, I think it is not clean. It will have many duplicate codes. If I want to change the setup or the base URL later, I have to change it in many files and it is tiring.
   To make it cleaner, maybe I can make a parent class for the setup, and other test classes can just extend it. This way I don't need to copy paste the same code again and again.

# Module 2

### Reflection

1. I fix the unused import and empty if statement that PMD catch. My strategy is i open github action log to see the error line, then i just delete the useless code in IntelliJ and push again.
2. Yes, i think my project already meet the CI/CD definition. For CI, github action will auto run the test and PMD scan everytime i push my code. Then for CD, Render will automatic deploy my app to internet when i merge the code to main branch (https://module-1-coding-standards.onrender.com/product/list).