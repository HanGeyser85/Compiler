#include <iostream>
#include <cstring>
#include <iomanip>
#include <cstdlib>
#include <string>
#include <cctype>    // NO other #include are allowed!
using namespace std; // NO other using are allowed!
// global
string s = "BABACABAB";
// NO other global variables are allowed!
// Here will be your Recursive Function implementation
// MY OWN CODE
string processString(const string &str, int counter = 0, string temp = "")
{
    bool flag = false;
    if (s.length() == 0) {
        return "mapping not possible";
    }

    if (counter == s.length()) {
        return"";
    }

    if (str[0] == 'C')
    {
        return "mapping not possible";
    }

    temp += str[counter];
    if (temp == "AC")
    {
        if (processString(str, counter + 1, "").find("mapping not possible")) {
            temp = "";
            return "Z" + processString(str, counter + 1, "");
        }
    }

    if (temp == "BAB"){
        if (processString(str, counter + 1, "").find("mapping not possible")) {
            temp = "";
            return "Y" + processString(str, counter + 1, "");
        }
    }

    if (temp == "ACBA")
    {
        if (processString(str, counter + 1, "").find("mapping not possible")) {
            temp = "";
            return "X" + processString(str, counter + 1, "");
        }
    }

    return processString(str, counter+1, temp)+"mapping not possible";
}
// MY OWN CODE

int main()
{

    // Here may perhaps be some other stuff that you might need ...

    // Here will be the call to your Recursive Function ...
    s = processString(s);

    // Here may perhaps be some other stuff that you might need ...

    
    // MY OWN CODE
    // Removes the "mapping not possible" from the string used for the backtracking
    s.erase(s.find("mapping not possible"), s.length()); 

    /* If the string is not mappable then it will be a very long concatenation of "mapping not possible" 
    and because all the "mapping not possible" are removed in the statement directly above, 
    the string will be empty and thus need to be readded once to display the error message correctly. */
    if (s.length() == 0)
    {
        s = "mapping not possible";
    }
    // MY OWN CODE

    cout << s; // Display the output string (after translation).
    // If the INPUT string cannot be translated then the output
    // string must contain the Error-Message.
    return 0;
}
