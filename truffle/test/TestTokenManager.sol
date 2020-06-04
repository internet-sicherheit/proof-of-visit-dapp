pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";



contract TestTokenManager {
    POVToken povtoken;
   

    

    //One token
    function testRequestToken1() public {
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
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(0),
            "failed"
        );

        //another address
        povtoken.requestToken(testlocationaddress, testUserAddress);
    }

    function testRequestToken2() public {
        //multiple requests

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
        povtoken.requestToken(testlocationaddress, testUserAddress);
        povtoken.requestToken(testlocationaddress, testUserAddress);

        Assert.equal(
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(0),
            "failed"
        );
        Assert.equal(
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(1),
            "failed"
        );
        Assert.equal(
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(2),
            "failed"
        );
    }

    function testRequestToken3() public {
        //multiple addresses

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
        address testUserAddress2 = 0x3f2B8eC9DaC8e7Bee8aa69Fc64C1779D8cCe5A85;

        povtoken.requestToken(testlocationaddress, testUserAddress);
        povtoken.requestToken(testlocationaddress, testUserAddress);
        povtoken.requestToken(testlocationaddress, testUserAddress2);
        povtoken.requestToken(testlocationaddress2, testUserAddress2);

        Assert.equal(
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(0),
            "failed"
        );
        Assert.equal(
            testUserAddress,
            povtoken.tokenIndexToOwnerAddress(1),
            "failed"
        );
        Assert.equal(
            testUserAddress2,
            povtoken.tokenIndexToOwnerAddress(2),
            "failed"
        );
        Assert.equal(
            testUserAddress2,
            povtoken.tokenIndexToOwnerAddress(3),
            "failed"
        );
    }

    
   

    

   

    // function testLocationNameOfToken() public {
    //     //setup
    //     povtoken = new POVToken();

    //         address testlocationaddress
    //      = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
    //     string memory testlocationname = "testname";
    //     string memory testtokenname = "testtoken";
    //     string memory testtokensymbol = "tt";

    //     setUpLocation(
    //         testtokenname,
    //         testtokensymbol,
    //         testlocationname,
    //         testlocationaddress
    //     );

    //         address testlocationaddress2
    //      = 0x43187Df86a4E2230c77Cb70918aB0e95C931Db5A;
    //     string memory testlocationname2 = "testname2";
    //     string memory testtokenname2 = "testtoken2";
    //     string memory testtokensymbol2 = "tt2";

    //     setUpLocation(
    //         testtokenname2,
    //         testtokensymbol2,
    //         testlocationname2,
    //         testlocationaddress2
    //     );
    //     //setup end

    //       address testUserAddress = 0xe506F9EE73160b3574f6c13942e0616f6096add8;
    //     povtoken.requestToken(testlocationaddress, testUserAddress);
    //     povtoken.requestToken(testlocationaddress2, testUserAddress);

    // Assert.equal(povtoken.locationNameOfToken(2), expected, "failed");

    //     Assert.notEqual(povtoken.locationNameOfToken(0), expected, "failed");
    //     Assert.notEqual(povtoken.locationNameOfToken(1), expected, "failed");
    //     Assert.equal(povtoken.locationNameOfToken(2), expected, "failed");
    // }

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
