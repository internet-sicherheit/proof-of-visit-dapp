pragma solidity ^0.5.16;



import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";

contract TestTokenManager{


	 POVToken povtoken;
    string _tokenname;
    string _tokensymbol;
    string _locationname;
    address _locationWalletAddress ;
	uint256 _locationId;

    



function testTotalSupply() public
{

povtoken = new POVToken();

	_locationname = "testname";
    	_locationWalletAddress = 0x793f3338d13D4DB8AC607Efa9101012f3cb3bE5A;
     	_locationId = 0;
    
uint256 expected = 1;


povtoken.addToken(_locationname, _locationWalletAddress, _locationId);

  Assert.equal(povtoken.totalSupply(), expected, "It should be 1 token in array ");

 
}

}