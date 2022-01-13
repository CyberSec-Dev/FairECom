package contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class Storage extends Contract {
    private static final String BINARY = "6080604052600060015534801561001557600080fd5b50610634806100256000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635d1ca6311461007d578063853255cc1461010d578063bd04747d14610138578063f2c298be146101de578063f2c9ecd814610247578063f9fbdb9e14610272575b600080fd5b34801561008957600080fd5b506100926102db565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100d25780820151818401526020810190506100b7565b50505050905090810190601f1680156100ff5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561011957600080fd5b50610122610380565b6040518082815260200191505060405180910390f35b34801561014457600080fd5b5061016360048036038101908080359060200190929190505050610386565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101a3578082015181840152602081019050610188565b50505050905090810190601f1680156101d05780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101ea57600080fd5b50610245600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061043a565b005b34801561025357600080fd5b5061025c610478565b6040518082815260200191505060405180910390f35b34801561027e57600080fd5b506102d9600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610482565b005b606060026000018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103765780601f1061034b57610100808354040283529160200191610376565b820191906000526020600020905b81548152906001019060200180831161035957829003601f168201915b5050505050905090565b60015481565b60606000808381526020019081526020016000208054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561042e5780601f106104035761010080835404028352916020019161042e565b820191906000526020600020905b81548152906001019060200180831161041157829003601f168201915b50505050509050919050565b60016000815480929190600101919050555080600080600154815260200190815260200160002090805190602001906104749291906104cf565b5050565b6000600154905090565b61048a61054f565b6020604051908101604052808381525090506104a5816104a9565b5050565b80600260008201518160000190805190602001906104c8929190610563565b5090505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061051057805160ff191683800117855561053e565b8280016001018555821561053e579182015b8281111561053d578251825591602001919060010190610522565b5b50905061054b91906105e3565b5090565b602060405190810160405280606081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106105a457805160ff19168380011785556105d2565b828001600101855582156105d2579182015b828111156105d15782518255916020019190600101906105b6565b5b5090506105df91906105e3565b5090565b61060591905b808211156106015760008160009055506001016105e9565b5090565b905600a165627a7a723058200822180f72629e0c9298c8500fbcc4954690f3273c523f9704669aeb9226d1ca0029\r\n";

    public static final String FUNC_GETID = "getId";

    public static final String FUNC_SUM = "sum";

    public static final String FUNC_GETPRICEROOT = "getPriceRoot";

    public static final String FUNC_REGISTER = "register";

    public static final String FUNC_GETNUMBER = "getNumber";

    public static final String FUNC_INIT = "init";

    @Deprecated
    protected Storage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Storage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Storage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> getId() {
        final Function function = new Function(FUNC_GETID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> sum() {
        final Function function = new Function(FUNC_SUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getPriceRoot(BigInteger id) {
        final Function function = new Function(FUNC_GETPRICEROOT, 
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> register(String name) {
        final Function function = new Function(
                FUNC_REGISTER, 
                Arrays.<Type>asList(new Utf8String(name)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getNumber() {
        final Function function = new Function(FUNC_GETNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> init(String id) {
        final Function function = new Function(
                FUNC_INIT, 
                Arrays.<Type>asList(new Utf8String(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Storage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Storage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Storage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Storage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Storage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Storage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Storage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Storage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Storage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Storage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Storage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Storage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
