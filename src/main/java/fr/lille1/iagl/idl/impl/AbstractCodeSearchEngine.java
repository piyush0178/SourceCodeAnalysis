package fr.lille1.iagl.idl.impl;

import java.util.List;

import fr.lille1.iagl.idl.CodeSearchEngine;

public abstract class AbstractCodeSearchEngine implements CodeSearchEngine {

	public class LocationImpl implements Location {
		public String filePath;
		public int lineNumber;

		@Override
		public String getFilePath() {
			return filePath;
		}

		@Override
		public int getLineNumber() {
			return lineNumber;
		}
	}

	public class Typeimpl implements Type {
		public String name;
		public String fullyQualifiedPackageName;
		public TypeKind kind;
		public Location declaration;

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getFullyQualifiedPackageName() {
			return fullyQualifiedPackageName;
		}

		@Override
		public TypeKind getKind() {
			return kind;
		}

		@Override
		public Location getDeclaration() {
			return declaration;
		}
	}

	public class MemberImpl implements Member {
		public Type declaringType;
		public Type type;
		public String name;

		@Override
		public Type getDeclaringType() {
			return declaringType;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	public class Fieldimpl implements Field {
		public Type declaringType;
		public Type type;
		public String name;

		@Override
		public Type getDeclaringType() {
			return declaringType;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public String getName() {
			return name;
		}
	}

	public class MethodImpl implements Method {
		public Type declaringType;
		public Type type;
		public String name;
		public List<Type> parameters;

		@Override
		public Type getDeclaringType() {
			return declaringType;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public List<Type> getParamaters() {
			return parameters;
		}
	}

}
