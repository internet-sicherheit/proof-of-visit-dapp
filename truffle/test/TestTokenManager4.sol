pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";


contract TestTokenManager4 {
    POVToken povtoken;

function testOwnerOf() public {
        //setup
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

        //setup end

        address testUserAddress = 0xe506F9EE73160b3574f6c13942e0616f6096add8;

        povtoken.requestToken(testlocationaddress, testUserAddress);

        Assert.equal(
            povtoken.ownerOf(0),
            testUserAddress,
            "not the same address"
        );
    }

function testTotalSupply() public {
        povtoken = new POVToken();

        //setup


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
        //setup end

        address testUserAddress = 0xe506F9EE73160b3574f6c13942e0616f6096add8;

        Assert.equal(
            0,
            povtoken.totalSupply(),
            "It should be 0 token in array "
        );

        povtoken.requestToken(testlocationaddress, testUserAddress);

        Assert.equal(
            1,
            povtoken.totalSupply(),
            "It should be 1 token in array "
        );
        povtoken.requestToken(testlocationaddress, testUserAddress);
        Assert.equal(
            2,
            povtoken.totalSupply(),
            "It should be 2 token in array "
        );
    }

    function testTotalSupplyLocations() public {
        povtoken = new POVToken();

        Assert.equal(
            0,
            povtoken.totalSupplyLocations(),
            "It should be no location in array "
        );


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
            povtoken.totalSupplyLocations(),
            "It should be 1 location in array "
        );
    }

function testBalanceOf() public {
        //setup
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


            address testlocationaddress2
         = 0x43187Df86a4E2230c77Cb70918aB0e95C931Db5A;
        string memory testlocationname2 = "testname2";
        string memory testtokenname2 = "testtoken2";
        string memory testtokensymbol2 = "tt2";

        setUpLocation(
            testtokenname2,
            testtokensymbol2,
            testlocationname2,
            testlocationaddress2
        );

        //setup end
        address testUserAddress = 0xe506F9EE73160b3574f6c13942e0616f6096add8;
        povtoken.requestToken(testlocationaddress, testUserAddress);
        povtoken.requestToken(testlocationaddress, testUserAddress);
        povtoken.requestToken(testlocationaddress2, testUserAddress);
        povtoken.requestToken(testlocationaddress2, testUserAddress);

        Assert.equal(
            4,
            povtoken.balanceOf(testUserAddress),
            "Should be 4 Tokens "
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