pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";


contract TestTokenManager {
    POVToken povtoken;

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
        Assert.equal(
            testlocationname,
            povtoken.getLocationNameFromLocationAddress(testlocationaddress),
            "Should be testname"
        );
        Assert.equal(
            testtokensymbol,
            povtoken.getTokensymbolFromLocationAddress(testlocationaddress),
            "Should be tt"
        );
        Assert.equal(
            testtokenname,
            povtoken.getTokennamefromLocationAddress(testlocationaddress),
            "should be testtoken"
        );
    }

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
