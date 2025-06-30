
type Props = {
    message:string
}

export default function ShowError(props: Readonly<Props>){

    return(
        <>
            <p className="text-red-800">{ props.message }</p>
        </>
    )
}