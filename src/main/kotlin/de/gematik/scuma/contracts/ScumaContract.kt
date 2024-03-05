/*
 * Copyright 2022-2024, gematik GmbH
 *
 * Licensed under the EUPL, Version 1.2 or - as soon they will be approved by the
 * European Commission – subsequent versions of the EUPL (the "Licence").
 * You may not use this work except in compliance with the Licence.
 *
 * You find a copy of the Licence in the "Licence" file or at
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expressed or implied.
 * In case of changes by gematik find details in the "Readme" file.
 *
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package de.gematik.scuma.contracts
import de.gematik.kether.abi.DataDecoder
import de.gematik.kether.abi.DataEncoder
import de.gematik.kether.abi.isTypeDynamic
import de.gematik.kether.abi.types.*
import de.gematik.kether.contracts.Contract
import de.gematik.kether.contracts.Event
import de.gematik.kether.eth.Eth
import de.gematik.kether.eth.types.*
import de.gematik.kether.extensions.hexToByteArray
import de.gematik.kether.extensions.keccak
import kotlinx.serialization.ExperimentalSerializationApi
import java.math.BigInteger
@OptIn(ExperimentalSerializationApi::class)
class ScumaContract(
eth: Eth,
baseTransaction: Transaction = Transaction()
) : Contract(eth, baseTransaction) {
companion object {
// deployment
val byteCode = "0x608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060018081600181540180825580915050039060005260206000200160009054906101000a905050600360018160018154018082558091505003906000526020600020905050612b81806100a56000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c80636cb0ecfb1161008c578063a31c8ebb11610066578063a31c8ebb14610221578063c82c7bb81461023d578063dff836e114610247578063edc922a914610265576100ea565b80636cb0ecfb146101b75780639892ae70146101e75780639f96de0f14610203576100ea565b80632b07fce3116100c85780632b07fce31461013157806346ce41751461016157806349f245371461017f57806353a5ee831461019b576100ea565b806305240e8b146100ef5780630e2600161461010b578063279ca46914610127575b600080fd5b61010960048036038101906101049190611f33565b610283565b005b61012560048036038101906101209190611f33565b6104d0565b005b61012f6106a0565b005b61014b60048036038101906101469190611f96565b61080e565b60405161015891906120bf565b60405180910390f35b6101696109dc565b60405161017691906120f0565b60405180910390f35b61019960048036038101906101949190611f96565b610a83565b005b6101b560048036038101906101b09190611f96565b610c77565b005b6101d160048036038101906101cc9190612170565b610e2b565b6040516101de91906122ae565b60405180910390f35b61020160048036038101906101fc91906122d0565b6112c3565b005b61020b61147e565b60405161021891906120f0565b60405180910390f35b61023b60048036038101906102369190612323565b611518565b005b6102456117a1565b005b61024f6118bf565b60405161025c9190612412565b60405180910390f35b61026d611a21565b60405161027a91906124e3565b60405180910390f35b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610311576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161030890612562565b60405180910390fd5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905060008111610398576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038f906125f4565b60405180910390fd5b60018080805490506103aa9190612643565b815481106103bb576103ba612677565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16600182815481106103fa576103f9612677565b5b9060005260206000200160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001805480610454576104536126a6565b5b6001900381819060005260206000200160006101000a81549073ffffffffffffffffffffffffffffffffffffffff02191690559055600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600090555050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461055e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161055590612562565b60405180910390fd5b6000600260008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020549050600081146105e5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016105dc90612747565b60405180910390fd5b6001829080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600180805490506106599190612643565b600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461072e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161072590612562565b60405180910390fd5b6000600190505b6001805490508110156107d657600260006001838154811061075a57610759612677565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000905580806107ce90612767565b915050610735565b50600160006107e59190611bd5565b60018081600181540180825580915050039060005260206000200160009054906101000a905050565b606060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161461089e576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161089590612562565b60405180910390fd5b600060056000848152602001908152602001600020549050600081116108f9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016108f090612821565b60405180910390fd5b6003818154811061090d5761090c612677565b5b9060005260206000209060020201600101805480602002602001604051908101604052809291908181526020016000905b828210156109d057838290600052602060002090600202016040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250508152602001906001019061093e565b50505050915050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a6d576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610a6490612562565b60405180910390fd5b60018080549050610a7e9190612643565b905090565b6000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411610b05576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610afc9061288d565b60405180910390fd5b60006005600083815260200190815260200160002054905060008114610b60576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610b579061291f565b60405180910390fd5b60046001816001815401808255809150500390600052602060002050506003600181600181540180825580915050039060005260206000209050508160036001600380549050610bb09190612643565b81548110610bc157610bc0612677565b5b90600052602060002090600202016000018190555060046001600480549050610bea9190612643565b81548110610bfb57610bfa612677565b5b9060005260206000200160036001600380549050610c199190612643565b81548110610c2a57610c29612677565b5b9060005260206000209060020201600101908054610c49929190611bf6565b506001600380549050610c5c9190612643565b60056000848152602001908152602001600020819055505050565b6000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411610cf9576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610cf09061288d565b60405180910390fd5b60006005600083815260200190815260200160002054905060008111610d54576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610d4b906129b1565b60405180910390fd5b60036001600380549050610d689190612643565b81548110610d7957610d78612677565b5b906000526020600020906002020160038281548110610d9b57610d9a612677565b5b9060005260206000209060020201600082015481600001556001820181600101908054610dc9929190611cbf565b509050506003805480610ddf57610dde6126a6565b5b6001900381819060005260206000209060020201600080820160009055600182016000610e0c9190611d88565b5050905560056000838152602001908152602001600020600090555050565b60606000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411610eaf576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401610ea69061288d565b60405180910390fd5b6000805b84849050811015611065576000858583818110610ed357610ed2612677565b5b905060400201803603810190610ee99190612ab2565b905060006005600083600001518152602001908152602001600020549050600081111561105057600060038281548110610f2657610f25612677565b5b9060005260206000209060020201905060005b816001018054905081101561104d576000826001018281548110610f6057610f5f612677565b5b90600052602060002090600202016040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152505090508a73ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1614801561102557506000856020015182602001511614155b1561103957868061103590612767565b9750505b50808061104590612767565b915050610f39565b50505b5050808061105d90612767565b915050610eb3565b5060008167ffffffffffffffff811115611082576110816129e7565b5b6040519080825280602002602001820160405280156110bb57816020015b6110a8611dac565b8152602001906001900390816110a05790505b5090506000915060005b858590508110156112b65760008686838181106110e5576110e4612677565b5b9050604002018036038101906110fb9190612ab2565b90506000600560008360000151815260200190815260200160002054905060008111156112a15760006003828154811061113857611137612677565b5b9060005260206000209060020201905060005b816001018054905081101561129e57600082600101828154811061117257611171612677565b5b90600052602060002090600202016040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152505090508b73ffffffffffffffffffffffffffffffffffffffff16816000015173ffffffffffffffffffffffffffffffffffffffff1614801561123757506000856020015182602001511614155b1561128a57604051806040016040528086600001518152602001866020015183602001511681525087898061126b90612767565b9a508151811061127e5761127d612677565b5b60200260200101819052505b50808061129690612767565b91505061114b565b50505b505080806112ae90612767565b9150506110c5565b5080925050509392505050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611351576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161134890612562565b60405180910390fd5b600060056000858152602001908152602001600020549050600081116113ac576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016113a390612821565b60405180910390fd5b600381815481106113c0576113bf612677565b5b906000526020600020906002020160010160405180604001604052808573ffffffffffffffffffffffffffffffffffffffff16815260200184815250908060018154018082558091505060019003906000526020600020906002020160009091909190915060008201518160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060208201518160010155505050505050565b600080600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411611501576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016114f89061288d565b60405180910390fd5b60016003805490506115139190612643565b905090565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146115a6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161159d90612562565b60405180910390fd5b60006005600084815260200190815260200160002054905060008111611601576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016115f890612821565b60405180910390fd5b60006003828154811061161757611616612677565b5b9060005260206000209060020201905080600101805490508310611670576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161166790612b2b565b60405180910390fd5b80600101600182600101805490506116889190612643565b8154811061169957611698612677565b5b90600052602060002090600202018160010184815481106116bd576116bc612677565b5b90600052602060002090600202016000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506001820154816001015590505080600101805480611753576117526126a6565b5b6001900381819060005260206000209060020201600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905560018201600090555050905550505050565b6000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411611823576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161181a9061288d565b60405180910390fd5b60005b60038054905081101561188257600560006003838154811061184b5761184a612677565b5b906000526020600020906002020160000154815260200190815260200160002060009055808061187a90612767565b915050611826565b50600360006118919190611dc6565b600360018160018154018082558091505003906000526020600020905050600460006118bd9190611dea565b565b60606000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205411611943576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161193a9061288d565b60405180910390fd5b600060016003805490506119579190612643565b67ffffffffffffffff8111156119705761196f6129e7565b5b60405190808252806020026020018201604052801561199e5781602001602082028036833780820191505090505b5090506000600190505b600380549050811015611a1957600381815481106119c9576119c8612677565b5b906000526020600020906002020160000154826001836119e99190612643565b815181106119fa576119f9612677565b5b6020026020010181815250508080611a1190612767565b9150506119a8565b508091505090565b606060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611ab1576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401611aa890612562565b60405180910390fd5b600060018080549050611ac49190612643565b67ffffffffffffffff811115611add57611adc6129e7565b5b604051908082528060200260200182016040528015611b0b5781602001602082028036833780820191505090505b5090506000600190505b600180549050811015611bcd5760018181548110611b3657611b35612677565b5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1682600183611b6f9190612643565b81518110611b8057611b7f612677565b5b602002602001019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508080611bc590612767565b915050611b15565b508091505090565b5080546000825590600052602060002090810190611bf39190611e0b565b50565b828054828255906000526020600020906002028101928215611cae5760005260206000209160020282015b82811115611cad5782826000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060018201548160010155505091600201919060020190611c21565b5b509050611cbb9190611e28565b5090565b828054828255906000526020600020906002028101928215611d775760005260206000209160020282015b82811115611d765782826000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060018201548160010155505091600201919060020190611cea565b5b509050611d849190611e28565b5090565b5080546000825560020290600052602060002090810190611da99190611e28565b50565b604051806040016040528060008152602001600081525090565b5080546000825560020290600052602060002090810190611de79190611e6e565b50565b5080546000825590600052602060002090810190611e089190611e9d565b50565b5b80821115611e24576000816000905550600101611e0c565b5090565b5b80821115611e6a57600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055600182016000905550600201611e29565b5090565b5b80821115611e9957600080820160009055600182016000611e909190611d88565b50600201611e6f565b5090565b5b80821115611ebd5760008181611eb49190611d88565b50600101611e9e565b5090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000611f0082611ed5565b9050919050565b611f1081611ef5565b8114611f1b57600080fd5b50565b600081359050611f2d81611f07565b92915050565b600060208284031215611f4957611f48611ecb565b5b6000611f5784828501611f1e565b91505092915050565b6000819050919050565b611f7381611f60565b8114611f7e57600080fd5b50565b600081359050611f9081611f6a565b92915050565b600060208284031215611fac57611fab611ecb565b5b6000611fba84828501611f81565b91505092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b611ff881611ef5565b82525050565b61200781611f60565b82525050565b6040820160008201516120236000850182611fef565b5060208201516120366020850182611ffe565b50505050565b6000612048838361200d565b60408301905092915050565b6000602082019050919050565b600061206c82611fc3565b6120768185611fce565b935061208183611fdf565b8060005b838110156120b2578151612099888261203c565b97506120a483612054565b925050600181019050612085565b5085935050505092915050565b600060208201905081810360008301526120d98184612061565b905092915050565b6120ea81611f60565b82525050565b600060208201905061210560008301846120e1565b92915050565b600080fd5b600080fd5b600080fd5b60008083601f8401126121305761212f61210b565b5b8235905067ffffffffffffffff81111561214d5761214c612110565b5b60208301915083604082028301111561216957612168612115565b5b9250929050565b60008060006040848603121561218957612188611ecb565b5b600061219786828701611f1e565b935050602084013567ffffffffffffffff8111156121b8576121b7611ed0565b5b6121c48682870161211a565b92509250509250925092565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b6040820160008201516122126000850182611ffe565b5060208201516122256020850182611ffe565b50505050565b600061223783836121fc565b60408301905092915050565b6000602082019050919050565b600061225b826121d0565b61226581856121db565b9350612270836121ec565b8060005b838110156122a1578151612288888261222b565b975061229383612243565b925050600181019050612274565b5085935050505092915050565b600060208201905081810360008301526122c88184612250565b905092915050565b6000806000606084860312156122e9576122e8611ecb565b5b60006122f786828701611f81565b935050602061230886828701611f1e565b925050604061231986828701611f81565b9150509250925092565b6000806040838503121561233a57612339611ecb565b5b600061234885828601611f81565b925050602061235985828601611f81565b9150509250929050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600061239b8383611ffe565b60208301905092915050565b6000602082019050919050565b60006123bf82612363565b6123c9818561236e565b93506123d48361237f565b8060005b838110156124055781516123ec888261238f565b97506123f7836123a7565b9250506001810190506123d8565b5085935050505092915050565b6000602082019050818103600083015261242c81846123b4565b905092915050565b600081519050919050565b600082825260208201905092915050565b6000819050602082019050919050565b600061246c8383611fef565b60208301905092915050565b6000602082019050919050565b600061249082612434565b61249a818561243f565b93506124a583612450565b8060005b838110156124d65781516124bd8882612460565b97506124c883612478565b9250506001810190506124a9565b5085935050505092915050565b600060208201905081810360008301526124fd8184612485565b905092915050565b600082825260208201905092915050565b7f4e6f74206f776e65720000000000000000000000000000000000000000000000600082015250565b600061254c600983612505565b915061255782612516565b602082019050919050565b6000602082019050818103600083015261257b8161253f565b9050919050565b7f72656a6563746564202d2070726f74656374696f6e20617574686f72697a617460008201527f696f6e20696420646f6573206e6f742065786973740000000000000000000000602082015250565b60006125de603583612505565b91506125e982612582565b604082019050919050565b6000602082019050818103600083015261260d816125d1565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052601160045260246000fd5b600061264e82611f60565b915061265983611f60565b92508282101561266c5761266b612614565b5b828203905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603160045260246000fd5b7f72656a6563746564202d2070726f74656374696f6e20617574686f72697a617460008201527f696f6e20696420697320616c7265616479207265676973746572656400000000602082015250565b6000612731603c83612505565b915061273c826126d5565b604082019050919050565b6000602082019050818103600083015261276081612724565b9050919050565b600061277282611f60565b91507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82036127a4576127a3612614565b5b600182019050919050565b7f70726f746563746564207265736f7572636520646f6573206e6f74206578697360008201527f7400000000000000000000000000000000000000000000000000000000000000602082015250565b600061280b602183612505565b9150612816826127af565b604082019050919050565b6000602082019050818103600083015261283a816127fe565b9050919050565b7f50726f7669646572206e6f7420617574686f72697a6564000000000000000000600082015250565b6000612877601783612505565b915061288282612841565b602082019050919050565b600060208201905081810360008301526128a68161286a565b9050919050565b7f72656a6563746564202d2070726f746563746564207265736f7572636520696460008201527f20697320616c7265616479207265676973746572656400000000000000000000602082015250565b6000612909603683612505565b9150612914826128ad565b604082019050919050565b60006020820190508181036000830152612938816128fc565b9050919050565b7f72656a6563746564202d2070726f746563746564207265736f7572636520696460008201527f20646f6573206e6f742065786973740000000000000000000000000000000000602082015250565b600061299b602f83612505565b91506129a68261293f565b604082019050919050565b600060208201905081810360008301526129ca8161298e565b9050919050565b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b612a1f826129d6565b810181811067ffffffffffffffff82111715612a3e57612a3d6129e7565b5b80604052505050565b6000612a51611ec1565b9050612a5d8282612a16565b919050565b600060408284031215612a7857612a776129d1565b5b612a826040612a47565b90506000612a9284828501611f81565b6000830152506020612aa684828501611f81565b60208301525092915050565b600060408284031215612ac857612ac7611ecb565b5b6000612ad684828501612a62565b91505092915050565b7f72756c6520646f6573206e6f7420657869737400000000000000000000000000600082015250565b6000612b15601383612505565b9150612b2082612adf565b602082019050919050565b60006020820190508181036000830152612b4481612b08565b905091905056fea2646970667358221220c17448483f0d5a224b71fa749ec926c33ad4776c29bf0a8b3a34ca82c40438dd64736f6c634300080d0033".hexToByteArray()
fun deploy(eth:Eth, from: Address): TransactionReceipt {
val params = Data(
byteCode + DataEncoder()
.build().toByteArray()
)
return deploy(eth, from, params)
}
// 4 byte selectors (functions) and topics (events)
val functionDeleteRule = "deleteRule(uint256,uint256)".keccak().copyOfRange(0, 4)
val functionGetPolicy = "getPolicy(uint256)".keccak().copyOfRange(0, 4)
val functionGetProviderCount = "getProviderCount()".keccak().copyOfRange(0, 4)
val functionGetProviders = "getProviders()".keccak().copyOfRange(0, 4)
val functionGetResourceCount = "getResourceCount()".keccak().copyOfRange(0, 4)
val functionGetResourceIds = "getResourceIds()".keccak().copyOfRange(0, 4)
val functionRegisterProvider = "registerProvider(address)".keccak().copyOfRange(0, 4)
val functionRegisterResource = "registerResource(uint256)".keccak().copyOfRange(0, 4)
val functionRequestPermissions = "requestPermissions(address,(uint256,uint256)[])".keccak().copyOfRange(0, 4)
val functionSetRule = "setRule(uint256,address,uint256)".keccak().copyOfRange(0, 4)
val functionUnregisterAllProviders = "unregisterAllProviders()".keccak().copyOfRange(0, 4)
val functionUnregisterAllResources = "unregisterAllResources()".keccak().copyOfRange(0, 4)
val functionUnregisterProvider = "unregisterProvider(address)".keccak().copyOfRange(0, 4)
val functionUnregisterResource = "unregisterResource(uint256)".keccak().copyOfRange(0, 4)
}
// tuples
data class PermissionRequest(val protectedResourceId: AbiUint256,val requestedMethods: AbiUint256) : AbiTuple {
constructor(dataDecoder: DataDecoder) : this(dataDecoder.next(AbiUint256::class),dataDecoder.next(AbiUint256::class))override fun encode(): DataEncoder {
return DataEncoder()
.encode(protectedResourceId)
.encode(requestedMethods)
}
companion object : Dynamic {
override fun isDynamic() = isTypeDynamic(AbiUint256::class)||isTypeDynamic(AbiUint256::class)}
}
data class Rule(val who: AbiAddress,val how: AbiUint256) : AbiTuple {
constructor(dataDecoder: DataDecoder) : this(dataDecoder.next(AbiAddress::class),dataDecoder.next(AbiUint256::class))override fun encode(): DataEncoder {
return DataEncoder()
.encode(who)
.encode(how)
}
companion object : Dynamic {
override fun isDynamic() = isTypeDynamic(AbiAddress::class)||isTypeDynamic(AbiUint256::class)}
}
data class Permission(val protectedResourceId: AbiUint256,val grantedMethods: AbiUint256) : AbiTuple {
constructor(dataDecoder: DataDecoder) : this(dataDecoder.next(AbiUint256::class),dataDecoder.next(AbiUint256::class))override fun encode(): DataEncoder {
return DataEncoder()
.encode(protectedResourceId)
.encode(grantedMethods)
}
companion object : Dynamic {
override fun isDynamic() = isTypeDynamic(AbiUint256::class)||isTypeDynamic(AbiUint256::class)}
}
// events
override val listOfEventDecoders: List<(Log) -> Event?> = listOf()
// functions
suspend fun deleteRule(protectedResourceId: AbiUint256,index: AbiUint256): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionDeleteRule))
.encode(protectedResourceId)
.encode(index).build()
return transact(params)
}
fun getPolicy(protectedResourceId: AbiUint256): List<Rule> {
val params = DataEncoder()
.encode(Data4(functionGetPolicy))
.encode(protectedResourceId).build()
val decoder = DataDecoder(call(params))
return decoder.next(Rule::class, -1) as List<Rule>}
fun getProviderCount(): AbiUint256 {
val params = DataEncoder()
.encode(Data4(functionGetProviderCount)).build()
val decoder = DataDecoder(call(params))
return decoder.next(AbiUint256::class) as AbiUint256}
fun getProviders(): List<AbiAddress> {
val params = DataEncoder()
.encode(Data4(functionGetProviders)).build()
val decoder = DataDecoder(call(params))
return decoder.next(AbiAddress::class, -1) as List<AbiAddress>}
fun getResourceCount(): AbiUint256 {
val params = DataEncoder()
.encode(Data4(functionGetResourceCount)).build()
val decoder = DataDecoder(call(params))
return decoder.next(AbiUint256::class) as AbiUint256}
fun getResourceIds(): List<AbiUint256> {
val params = DataEncoder()
.encode(Data4(functionGetResourceIds)).build()
val decoder = DataDecoder(call(params))
return decoder.next(AbiUint256::class, -1) as List<AbiUint256>}
suspend fun registerProvider(protectionAuthorizationId: AbiAddress): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionRegisterProvider))
.encode(protectionAuthorizationId).build()
return transact(params)
}
suspend fun registerResource(protectedResourceId: AbiUint256): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionRegisterResource))
.encode(protectedResourceId).build()
return transact(params)
}
fun requestPermissions(userId: AbiAddress,permissionRequests: List<PermissionRequest>): List<Permission> {
val params = DataEncoder()
.encode(Data4(functionRequestPermissions))
.encode(userId)
.encode(permissionRequests, -1).build()
val decoder = DataDecoder(call(params))
return decoder.next(Permission::class, -1) as List<Permission>}
suspend fun setRule(protectedResourceId: AbiUint256,userId: AbiAddress,methods: AbiUint256): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionSetRule))
.encode(protectedResourceId)
.encode(userId)
.encode(methods).build()
return transact(params)
}
suspend fun unregisterAllProviders(): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionUnregisterAllProviders)).build()
return transact(params)
}
suspend fun unregisterAllResources(): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionUnregisterAllResources)).build()
return transact(params)
}
suspend fun unregisterProvider(protectionAuthorizationId: AbiAddress): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionUnregisterProvider))
.encode(protectionAuthorizationId).build()
return transact(params)
}
suspend fun unregisterResource(protectedResourceId: AbiUint256): TransactionReceipt {
val params = DataEncoder()
.encode(Data4(functionUnregisterResource))
.encode(protectedResourceId).build()
return transact(params)
}
}
