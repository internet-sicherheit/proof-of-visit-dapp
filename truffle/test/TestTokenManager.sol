pragma solidity ^0.5.16;



import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "..//contracts/TokenManager.sol";

contract TestTokenManager{

    string _tokenname;
    string _tokensymbol;
    string _locationname;
    address _locationWalletAddress ;

    
function beforeAll() public 
{
}

function beforeEach() public {

    	_tokenname = "TestName";
    	_tokensymbol = "TS";
    	_locationname = "TestLocation";
    	_locationWalletAddress = 0x30f186b022B80feFf60208c0749a177AF17aCF0e;
  
   
} 

    function testCreateLocation() public {
        TokenManager tokenmanager = TokenManager(DeployedAddresses.TokenManager());
	 tokenmanager.setLocationNameForId(0,"test"); //first field id 0
    
    }
    
    
}