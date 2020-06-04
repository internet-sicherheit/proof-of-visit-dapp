pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";


contract TestTokenManager2 {
    POVToken povtoken;




 //trying to create multiple locations from same address
    function testCreateLocation2() public {
        povtoken = new POVToken();


            address testlocationaddress
         = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
        string memory testlocationname = "testname";
        string memory testtokenname = "testtoken";
        string memory testtokensymbol = "tt";

        setUpLocation(
            testtokenname,
            testtokensymbol,
            testlocationname,
            testlocationaddress
        );

        string memory testlocationname2 = "testname2";
        string memory testtokenname2 = "testtoken2";
        string memory testtokensymbol2 = "tt2";

        setUpLocation(
            testtokenname2,
            testtokensymbol2,
            testlocationname2,
            testlocationaddress
        );

        Assert.equal(
            1,
            povtoken.getAmountLocations(),
            "should be only 1 location"
        );

     
    }

    function testCreateLocation3() public {
        povtoken = new POVToken();


//first
            address testlocationaddress
         = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
        string memory testlocationname = "testname";
        string memory testtokenname = "testtoken";
        string memory testtokensymbol = "tt";

        setUpLocation(
            testtokenname,
            testtokensymbol,
            testlocationname,
            testlocationaddress
        );

        //same locatenname
        string memory testtokenname2 = "testtoken2";
        string memory testtokensymbol2 = "tt2";
        address testlocationaddress2 = 0x3DA80eb50FD7F5c8D99Fd73BCbDbE9691016f554;

        setUpLocation(
            testtokenname2,
            testtokensymbol2,
            testlocationname,
            testlocationaddress2
        );

        //same tokensymbol
            address testlocationaddress3
         = 0x2b986de8fc9810568f35cd9e233c68F0a517c8AE;
        string memory testlocationname3 = "testname3";
        string memory testtokenname3 = "testtoken3";
     

        setUpLocation(
            testtokenname3,
            testtokensymbol,
            testlocationname3,
            testlocationaddress3
        );

        Assert.equal(
            1,
            povtoken.getAmountLocations(),
            "should be only 1 location"
        );


   //same tokensymbol
            address testlocationaddress4
         = 0x8d14585Eb7cCDF8E8ce571Bc38B983f97F067980;
        string memory testlocationname4 = "testname4";
        string memory testtokenname4 = "testtoken4";
         string memory testtokensymbol4 = "tt4";
     

        setUpLocation(
            testtokenname4,
            testtokensymbol4,
            testlocationname4,
            testlocationaddress4
        );

    }

    function testCreateLocation() public {
        povtoken = new POVToken();


            address testlocationaddress
         = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
        string memory testlocationname = "testname";
        string memory testtokenname = "testtoken";
        string memory testtokensymbol = "tt";

        setUpLocation(
            testtokenname,
            testtokensymbol,
            testlocationname,
            testlocationaddress
        );

        Assert.equal(
            1,
            povtoken.getAmountLocations(),
            "There should be 1 Location"
        );
       
    }

    



function setUpLocation(
        string memory testtokenname,
        string memory testsymbol,
        string memory testlocationname,
        address testaddress
    ) public {
        povtoken.createLocation(
            testtokenname,
            testsymbol,
            testlocationname,
            testaddress
        );
    }

}
