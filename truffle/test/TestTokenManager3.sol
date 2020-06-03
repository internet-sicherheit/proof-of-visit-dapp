pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";


contract TestTokenManager3 {
    POVToken povtoken;

    // function test() public {
    //     povtoken = new POVToken();


    //         address testlocationaddress
    //      = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;

    //     string memory bla = '0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A';

    //     Assert.equal(bla, povtoken.addressToString(0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A), "failed");
    // }

    function testGetUserTokenList() public {
        povtoken = new POVToken();



        
            address testlocationaddress
         = 0xFDB3D379ab844A402d8D3369077E074318b9Fa01;
        string memory testlocationname = "testname";
        string memory testtokenname = "testtoken";
        string memory testtokensymbol = "tt";

        setUpLocation(
            testtokenname,
            testtokensymbol,
            testlocationname,
            testlocationaddress
        );

        address testUserAddress = 0xe506F9EE73160b3574f6c13942e0616f6096add8;
        povtoken.requestToken(testlocationaddress, testUserAddress);


            string memory expectedJson
         ='[{"locationaddress":"0xfdb3d379ab844a402d8d3369077e074318b9fa01","locationname":"testname","tokenname":"testtoken","tokensymbol":"tt","token":0"}]';

        Assert.equal(
            expectedJson,
            povtoken.getUserTokenlist(testUserAddress),
            "failed"
        );

  // Assert.equal(true,povtoken.compareStrings(expectedJson,povtoken.getUserTokenlist(testUserAddress)),"failed");

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
