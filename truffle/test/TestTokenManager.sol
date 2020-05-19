pragma solidity ^0.5.16;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";


contract TestTokenManager {
    
  
POVToken povtoken;
  

    function testCreateLocation() public {

  povtoken = new POVToken();

    address testlocationaddress = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
    string memory testlocationname = "testname";
    string memory testtokenname = "testtoken";
    string memory testtokensymbol = "tt";

   

    setUpLocation(  testtokenname, testtokensymbol, testlocationname, testlocationaddress ); 

   Assert.equal(1,  povtoken.getAmountLocations(), "There should be 1 Location");
    Assert.equal(testlocationname,  povtoken.getLocationNameFromLocationAddress(testlocationaddress), "Should be testname");
    Assert.equal(testtokensymbol,  povtoken.getTokensymbolFromLocationAddress(testlocationaddress), "Should be tt");
     Assert.equal(testtokenname,  povtoken.getTokennamefromLocationAddress(testlocationaddress), "should be testtoken");






    }

    // function testTotalSupply() public {
    //     uint256 expected = 1;

    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );
    //     povtoken.requestToken(0, _locationWalletAddress, _requestaddress);

       

    //     Assert.equal(
    //         povtoken.totalSupply(),
    //         expected,
    //         "It should be 1 token in array "
    //     );
    // }

   

    // function testTotalSupplyLocations() public {
    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );

    //     Assert.equal(
    //         povtoken.totalSupplyLocations(),
    //         expected1,
    //         "It should be 1 location in array "
    //     );
    //     Assert.notEqual(
    //         povtoken.totalSupplyLocations(),
    //         expected2,
    //         "there are no locations but there should be at least 1"
    //     );
    // }

    // function testOwnerOf() public {
    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );
    //     povtoken.requestToken(
    //         _locationId,
    //         _locationWalletAddress,
    //         _requestaddress
    //     );

    //     address expectedaddress = 0x19d7a2a11b6957912AC232a10a02B5FdD0df9A17;

    //     Assert.equal(povtoken.ownerOf(0), expectedaddress, "?");
    // }

    // function testBalanceOf() public {
    //     uint256 expected = 3;

    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );
    //     povtoken.requestToken(
    //         _locationId,
    //         _locationWalletAddress,
    //         _requestaddress
    //     );
    //     povtoken.requestToken(
    //         _locationId,
    //         _locationWalletAddress,
    //         _requestaddress
    //     );
    //     povtoken.requestToken(
    //         _locationId,
    //         _locationWalletAddress,
    //         _requestaddress
    //     );

    //     Assert.equal(
    //         povtoken.balanceOf(_requestaddress),
    //         expected,
    //         "Should be 3 Tokens "
    //     );
    // }

    // function testLocationNameOfToken() public {
    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );
    //     povtoken.requestToken(0, _locationWalletAddress, _requestaddress);
    //     povtoken.requestToken(0, _locationWalletAddress, _requestaddress);

    //     string memory expected = _locationname;

    //     Assert.equal(povtoken.locationNameOfToken(0), expected, "failed");
    //     Assert.equal(povtoken.locationNameOfToken(1), expected, "failed");

    //     _locationname = "testname2";

    //     povtoken.createLocation(
    //         _tokenname,
    //         _tokensymbol,
    //         _locationname,
    //         _locationWalletAddress
    //     );
    //     povtoken.requestToken(1, _locationWalletAddress, _requestaddress);

    //     expected = "testname2";

    //     Assert.notEqual(povtoken.locationNameOfToken(0), expected, "failed");
    //     Assert.notEqual(povtoken.locationNameOfToken(1), expected, "failed");
    //     Assert.equal(povtoken.locationNameOfToken(2), expected, "failed");
    // }


    function setUpLocation(string memory testtokenname, string memory testsymbol, string memory testlocationname, address testaddress) public 

    {

        povtoken.createLocation(
            testtokenname,
            testsymbol,
            testlocationname,
            testaddress
        );



    }
}
