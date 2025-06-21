
type Props = {
    message:string
}

export default function ShowError(props: Readonly<Props>){

    return(
        <>
            <span className="text-red-700">{ props.message }</span>
        </>
    )
}